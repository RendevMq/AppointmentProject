package com.appointment.service.implementation;
import com.appointment.persistence.entity.authEntities.UserEntity;
import com.appointment.persistence.repository.UserRepository;
import com.appointment.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IUserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findUserEntityByUsername(username);
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean isAdmin() {
        // TODO: Aquí deberíamos verificar si el usuario actual es un Admin
        return false;
    }
}


