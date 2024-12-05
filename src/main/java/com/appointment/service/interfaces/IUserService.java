package com.appointment.service.interfaces;

import com.appointment.presentation.dto.authDto.AuthCreateUserRequest;
import com.appointment.presentation.dto.authDto.AuthResponse;
import com.appointment.presentation.dto.authDto.AuthLoginRequest;
import com.appointment.persistence.entity.authEntities.UserEntity;
import java.util.Optional;

public interface IUserService {

//    AuthResponse createUser(AuthCreateUserRequest userRequest);
//
//    AuthResponse loginUser(AuthLoginRequest loginRequest);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserEntity> getUserByUsername(String username);

    Optional<UserEntity> getUserById(Long id);

    // Verificar si el usuario es administrador
    boolean isAdmin();
}
