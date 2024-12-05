package com.appointment.presentation.dto.authDto;


import com.appointment.persistence.entity.authEntities.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthCreateUserRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String email,
        RoleEnum role) {  // Aquí usamos RoleEnum directamente (el campo puede ser null)
}
