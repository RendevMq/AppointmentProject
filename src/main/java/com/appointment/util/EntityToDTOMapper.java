package com.appointment.util;


import com.appointment.persistence.entity.AppointmentEntity;
import com.appointment.persistence.entity.AppointmentStatus;
import com.appointment.persistence.entity.authEntities.UserEntity;
import com.appointment.presentation.dto.AppointmentAssignRequestDto;
import com.appointment.presentation.dto.AppointmentCompleteRequestDto;
import com.appointment.presentation.dto.AppointmentCreateRequestDto;
import com.appointment.presentation.dto.AppointmentDto;
import com.appointment.presentation.dto.AppointmentReopenRequestDto;
import com.appointment.presentation.dto.authDto.AuthCreateUserRequest;
import com.appointment.presentation.dto.authDto.AuthResponse;
import com.appointment.presentation.dto.authDto.AuthLoginRequest;

import java.time.LocalDateTime;
import java.util.Optional;

public class EntityToDTOMapper {

    // Mapeo de AppointmentEntity a AppointmentDto
    public static AppointmentDto mapToAppointmentDto(AppointmentEntity appointmentEntity) {
        return AppointmentDto.builder()
                .id(appointmentEntity.getId())
                .appointmentDate(appointmentEntity.getAppointmentDate())
                .project(appointmentEntity.getProject())
                .consultation(appointmentEntity.getConsultation())
                .clientId(appointmentEntity.getClient().getId())
                .clientName(appointmentEntity.getClient().getUsername())  // Asumimos que el cliente tiene un username
                .assignedAgentId(appointmentEntity.getAssignedAgent() != null ? appointmentEntity.getAssignedAgent().getId() : null)
                .assignedAgentName(appointmentEntity.getAssignedAgent() != null ? appointmentEntity.getAssignedAgent().getUsername() : null)
                .assignmentDate(appointmentEntity.getAssignmentDate())
                .status(appointmentEntity.getStatus())
                .completedDate(appointmentEntity.getCompletedDate())
                .reopenedDate(appointmentEntity.getReopenedDate())
                .build();
    }

    // Mapeo de AppointmentAssignRequestDto a AppointmentEntity
    public static AppointmentEntity mapToAppointmentEntity(AppointmentAssignRequestDto appointmentAssignRequestDto, UserEntity agent, AppointmentEntity appointmentEntity) {
        appointmentEntity.setAssignedAgent(agent);
        return appointmentEntity;
    }

    // Mapeo de AppointmentCreateRequestDto a AppointmentEntity
    public static AppointmentEntity mapToAppointmentEntity(AppointmentCreateRequestDto appointmentCreateRequestDto, UserEntity client) {
        return AppointmentEntity.builder()
                .appointmentDate(appointmentCreateRequestDto.getAppointmentDate())
                .project(appointmentCreateRequestDto.getProject())
                .consultation(appointmentCreateRequestDto.getQuery())
                .client(client)
                .status(AppointmentStatus.PENDING) // Estado por defecto
                .build();
    }

    // Mapeo de AppointmentCompleteRequestDto a AppointmentEntity
    public static AppointmentEntity mapToAppointmentCompleteEntity(AppointmentCompleteRequestDto appointmentCompleteRequestDto, AppointmentEntity appointmentEntity) {
        appointmentEntity.setStatus(AppointmentStatus.COMPLETED);
        appointmentEntity.setCompletedDate(appointmentEntity.getCompletedDate() != null ? appointmentEntity.getCompletedDate() : LocalDateTime.now());
        return appointmentEntity;
    }

    // Mapeo de AppointmentReopenRequestDto a AppointmentEntity
    public static AppointmentEntity mapToAppointmentReopenEntity(AppointmentReopenRequestDto appointmentReopenRequestDto, AppointmentEntity appointmentEntity) {
        appointmentEntity.setStatus(AppointmentStatus.REOPENED);
        appointmentEntity.setReopenedDate(LocalDateTime.now());
        return appointmentEntity;
    }

    // Mapeo de UserEntity a AuthResponse (para respuesta de autenticación)
    public static AuthResponse mapToAuthResponse(UserEntity userEntity, String accessToken) {
        return new AuthResponse(
                userEntity.getUsername(),
                "User successfully authenticated",
                accessToken,
                true
        );
    }

    // Mapeo de UserEntity a AuthCreateUserRequest (para crear usuario)
    public static AuthCreateUserRequest mapToAuthCreateUserRequest(UserEntity userEntity) {
        return new AuthCreateUserRequest(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getRole().getRoleEnum() // Mapeamos el RoleEnum desde RoleEntity
        );
    }

    // Mapeo de AuthLoginRequest a UserEntity (en base a su username)
    public static Optional<UserEntity> mapToUserEntity(AuthLoginRequest authLoginRequest, UserEntity userEntity) {
        if (userEntity.getUsername().equals(authLoginRequest.username())) {
            return Optional.of(userEntity);
        }
        return Optional.empty();
    }
}
