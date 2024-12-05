package com.appointment.persistence.repository;

import com.appointment.persistence.entity.authEntities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    // Encontrar un usuario por su nombre de usuario
    Optional<UserEntity> findUserEntityByUsername(String username);

    // Verificar si un usuario con el nombre de usuario existe
    boolean existsByUsername(String username);

    // Verificar si un usuario con el email existe
    boolean existsByEmail(String email);

    // Encontrar un usuario por su ID
    Optional<UserEntity> findById(Long id);

}