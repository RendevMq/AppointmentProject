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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IClientAppointmentServiceImpl implements IClientAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    // Método para obtener el usuario autenticado desde el contexto de seguridad
    private UserEntity getAuthenticatedUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserEntityByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Usuario no autenticado"));
    }

    @Override
    public AppointmentDto createAppointment(AppointmentCreateRequestDto appointmentRequestDto) {
        UserEntity client = getAuthenticatedUser();

        AppointmentEntity appointmentEntity = EntityToDTOMapper.mapToAppointmentEntity(appointmentRequestDto, client);
        AppointmentEntity savedAppointment = appointmentRepository.save(appointmentEntity);

        return EntityToDTOMapper.mapToAppointmentDto(savedAppointment);
    }

    @Override
    public List<AppointmentDto> getAppointmentsByClientId(Long clientId) {
        UserEntity authenticatedUser = getAuthenticatedUser();

        // Verificamos que el cliente autenticado sea el mismo que el cliente que está solicitando las citas
        if (!authenticatedUser.getId().equals(clientId)) {
            throw new RuntimeException("No tienes acceso a las citas de otro cliente.");
        }

        List<AppointmentEntity> appointments = appointmentRepository.findByClient_Id(clientId);

        // Convertir a DTO
        return appointments.stream()
                .map(EntityToDTOMapper::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDto getAppointmentDetails(Long appointmentId, Long clientId) {
        UserEntity authenticatedUser = getAuthenticatedUser();

        // Verificamos que el cliente autenticado sea el mismo que el cliente que está solicitando la cita
        if (!authenticatedUser.getId().equals(clientId)) {
            throw new RuntimeException("No tienes acceso a los detalles de esta cita.");
        }

        AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("La cita no existe"));

        if (!appointmentEntity.getClient().getId().equals(clientId)) {
            throw new RuntimeException("No tienes acceso a los detalles de esta cita.");
        }

        return EntityToDTOMapper.mapToAppointmentDto(appointmentEntity);
    }
}
