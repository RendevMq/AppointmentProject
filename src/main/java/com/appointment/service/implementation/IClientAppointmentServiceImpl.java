package com.appointment.service.implementation;

import com.appointment.persistence.entity.AppointmentEntity;
import com.appointment.persistence.entity.authEntities.UserEntity;
import com.appointment.persistence.repository.AppointmentRepository;
import com.appointment.persistence.repository.UserRepository;
import com.appointment.presentation.dto.AppointmentCreateRequestDto;
import com.appointment.presentation.dto.AppointmentDto;
import com.appointment.service.interfaces.IClientAppointmentService;
import com.appointment.util.EntityToDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IClientAppointmentServiceImpl implements IClientAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    // Obtener el usuario autenticado desde el contexto de seguridad
    private UserEntity getAuthenticatedUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserEntityByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Usuario no autenticado"));
    }


    @Override
    public AppointmentDto createAppointment(AppointmentCreateRequestDto appointmentRequestDto) {
        UserEntity client = getAuthenticatedUser();

        if (appointmentRequestDto.getAppointmentDate() == null) {
            throw new RuntimeException("La fecha de la cita es obligatoria");
        }

        // Crear la cita, asignando el cliente autenticado automáticamente
        AppointmentEntity appointmentEntity = EntityToDTOMapper.mapToAppointmentEntity(appointmentRequestDto, client);
        AppointmentEntity savedAppointment = appointmentRepository.save(appointmentEntity);

        return EntityToDTOMapper.mapToAppointmentDto(savedAppointment);
    }

    @Override
    public List<AppointmentDto> getAppointmentsByAuthenticatedClient() {
        UserEntity authenticatedUser = getAuthenticatedUser();

        // Obtener todas las citas del cliente autenticado
        List<AppointmentEntity> appointments = appointmentRepository.findByClient_Id(authenticatedUser.getId());

        // Convertir las citas a DTO y devolverlas
        return appointments.stream()
                .map(EntityToDTOMapper::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDto getAppointmentDetails(Long appointmentId) {
        UserEntity authenticatedUser = getAuthenticatedUser();

        // Buscar la cita por ID
        AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("La cita no existe"));

        // Verificar que la cita pertenece al cliente autenticado
        if (!appointmentEntity.getClient().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("No tienes acceso a los detalles de esta cita.");
        }

        // Devolver la cita mapeada a DTO
        return EntityToDTOMapper.mapToAppointmentDto(appointmentEntity);
    }
}
