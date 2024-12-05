package com.appointment.service.implementation;

import com.appointment.persistence.entity.AppointmentEntity;
import com.appointment.persistence.entity.AppointmentStatus;
import com.appointment.persistence.entity.authEntities.UserEntity;
import com.appointment.persistence.repository.AppointmentRepository;
import com.appointment.persistence.repository.UserRepository;
import com.appointment.presentation.dto.AppointmentDto;
import com.appointment.presentation.dto.UserDto;
import com.appointment.service.interfaces.IAdminAppointmentService;
import com.appointment.util.EntityToDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        // Permitir asignación si la cita está en estado PENDING o PENDING_REOPENED
        if (appointmentEntity.getStatus() != AppointmentStatus.PENDING && appointmentEntity.getStatus() != AppointmentStatus.PENDING_REOPENED) {
            throw new RuntimeException("La cita no se puede asignar, no está en estado PENDIENTE ni REABIERTO");
        }


        appointmentEntity.setAssignedAgent(agent);
        appointmentEntity.setStatus(AppointmentStatus.ASSIGNED);

        // Asignar la fecha de la asignación
        appointmentEntity.setAssignmentDate(LocalDateTime.now());

        appointmentRepository.save(appointmentEntity);
    }

    @Override
    public List<AppointmentDto> getAllPendingAppointments() {
        // Citas con estado PENDING o PENDING_REOPENED y sin agente asignado
        List<AppointmentEntity> pendingAppointments = appointmentRepository.findByStatusInAndAssignedAgentIsNull(
                List.of(AppointmentStatus.PENDING, AppointmentStatus.PENDING_REOPENED));

        // Mapeamos AppointmentEntity a AppointmentDto
        return pendingAppointments.stream()
                .map(EntityToDTOMapper::mapToAppointmentDto)
                .collect(Collectors.toList()); // Recoge los AppointmentDto en una lista
    }


    @Override
    public List<AppointmentDto> getAllAppointments() {

        List<AppointmentEntity> allAppointments = (List<AppointmentEntity>) appointmentRepository.findAll();

        return allAppointments.stream()
                .map(EntityToDTOMapper::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> allUsers = (List<UserEntity>) userRepository.findAll(); // Obtener todos los usuarios

        // Convertir los usuarios a DTOs
        return allUsers.stream()
                .map(EntityToDTOMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDto reopenAppointment(Long appointmentId) {
        Optional<AppointmentEntity> appointmentEntityOptional = appointmentRepository.findById(appointmentId);

        // Verificar si la cita existe
        if (appointmentEntityOptional.isEmpty()) {
            throw new RuntimeException("Cita no encontrada");
        }

        AppointmentEntity appointmentEntity = appointmentEntityOptional.get();

        // Verificar si la cita está en estado ASSIGNED para poder reabrirla
        if (appointmentEntity.getStatus() != AppointmentStatus.ASSIGNED) {
            throw new RuntimeException("La cita no puede ser reabierta porque no está asignada");
        }

        // Cambiar el estado a PENDING_REOPENED
        appointmentEntity.setStatus(AppointmentStatus.PENDING_REOPENED);

        // Limpiar los datos de asignación de agente
        appointmentEntity.setAssignedAgent(null);
        // appointmentEntity.setAssignedAgentName(null);  // Si es necesario también limpiar este campo
        appointmentEntity.setAssignmentDate(null);

        // Establecer la fecha de reapertura
        appointmentEntity.setReopenedDate(LocalDateTime.now());

        // Guardar los cambios
        appointmentRepository.save(appointmentEntity);

        // Devolver el DTO actualizado
        return EntityToDTOMapper.mapToAppointmentDto(appointmentEntity);
    }


}
