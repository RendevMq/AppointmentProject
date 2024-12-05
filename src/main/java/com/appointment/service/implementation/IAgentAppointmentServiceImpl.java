package com.appointment.service.implementation;

import com.appointment.persistence.entity.AppointmentEntity;
import com.appointment.persistence.entity.AppointmentStatus;
import com.appointment.persistence.repository.AppointmentRepository;
import com.appointment.presentation.dto.AppointmentDto;
import com.appointment.service.interfaces.IAgentAppointmentService;
import com.appointment.util.EntityToDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IAgentAppointmentServiceImpl implements IAgentAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<AppointmentDto> getAssignedAppointments() {
        // Obtener todas las citas asignadas al agente (aquí se asume que el agente está relacionado con la cita)
        // El agente debe ser el que esté asignado a la cita
        List<AppointmentEntity> assignedAppointments = appointmentRepository.findByAssignedAgentIsNotNull();

        // Mapear las citas de AppointmentEntity a AppointmentDto
        return assignedAppointments.stream()
                .map(EntityToDTOMapper::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void attendAppointment(Long appointmentId) {
        Optional<AppointmentEntity> appointmentEntityOptional = appointmentRepository.findById(appointmentId);

        if (appointmentEntityOptional.isEmpty()) {
            throw new RuntimeException("Cita no encontrada");
        }

        AppointmentEntity appointmentEntity = appointmentEntityOptional.get();

        if (appointmentEntity.getStatus() != AppointmentStatus.ASSIGNED) {
            throw new RuntimeException("La cita no está en estado ASIGNADA");
        }

        // Actualizamos la cita a COMPLETADA
        appointmentEntity.setStatus(AppointmentStatus.COMPLETED);
        appointmentEntity.setCompletedDate(appointmentEntity.getCompletedDate() == null ? java.time.LocalDateTime.now() : appointmentEntity.getCompletedDate());

        appointmentRepository.save(appointmentEntity);
    }
}
