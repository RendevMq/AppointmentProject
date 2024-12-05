package com.appointment.service.implementation;

import com.appointment.persistence.entity.AppointmentEntity;
import com.appointment.persistence.entity.AppointmentStatus;
import com.appointment.persistence.entity.authEntities.UserEntity;
import com.appointment.persistence.repository.AppointmentRepository;
import com.appointment.persistence.repository.UserRepository;
import com.appointment.presentation.dto.AppointmentDto;
import com.appointment.service.interfaces.IAdminAppointmentService;
import com.appointment.util.EntityToDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IAdminAppointmentServiceImpl implements IAdminAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void assignAppointmentToAgent(Long appointmentId, Long agentId) {
        Optional<AppointmentEntity> appointmentEntityOptional = appointmentRepository.findById(appointmentId);
        Optional<UserEntity> agentOptional = userRepository.findById(agentId);

        if (appointmentEntityOptional.isEmpty() || agentOptional.isEmpty()) {
            throw new RuntimeException("Cita o Agente no encontrados");
        }

        AppointmentEntity appointmentEntity = appointmentEntityOptional.get();
        UserEntity agent = agentOptional.get();

        if (appointmentEntity.getStatus() != AppointmentStatus.PENDING) {
            throw new RuntimeException("La cita no se puede asignar, no está en estado PENDIENTE");
        }

        appointmentEntity.setAssignedAgent(agent);
        appointmentEntity.setStatus(AppointmentStatus.ASSIGNED);

        appointmentRepository.save(appointmentEntity);
    }

    @Override
    public List<AppointmentDto> getAllPendingAppointments() {
        List<AppointmentEntity> pendingAppointments = appointmentRepository.findByStatusAndAssignedAgentIsNull(AppointmentStatus.PENDING);

        // Mapea cada AppointmentEntity a AppointmentDto
        return pendingAppointments.stream()
                .map(EntityToDTOMapper::mapToAppointmentDto)
                .collect(Collectors.toList()); // Recoge los AppointmentDto en una lista
    }

}
