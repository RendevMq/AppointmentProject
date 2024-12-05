package com.appointment.service.implementation;

import com.appointment.persistence.entity.authEntities.RoleEntity;
import com.appointment.persistence.entity.authEntities.RoleEnum;
import com.appointment.persistence.entity.authEntities.UserEntity;
import com.appointment.persistence.repository.RoleRepository;
import com.appointment.persistence.repository.UserRepository;
import com.appointment.presentation.dto.authDto.AuthCreateUserRequest;
import com.appointment.presentation.dto.authDto.AuthLoginRequest;
import com.appointment.presentation.dto.authDto.AuthResponse;
import com.appointment.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con email " + username + " no existe"));

        // Usamos el primer rol
        List<SimpleGrantedAuthority> authorityList = List.of(
                new SimpleGrantedAuthority("ROLE_".concat(userEntity.getRole().getRoleEnum().name()))
        );


        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), // Cambiado para usar email
                userEntity.getPassword(),
                userEntity.isEnable(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String email = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponse(email, "Usuario ingresado con éxito", accessToken, true);
    }

    public Authentication authenticate(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);  // Cambiado para usar email

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Contraseña inválida");
        }
        return new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
    }

    // En el UserDetailsServiceImpl (o servicio relacionado con la autorización)
    public boolean isAdminUser() {
        // Obtiene el rol del usuario autenticado
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }


    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        String email = authCreateUserRequest.email();

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso");
        }

        RoleEnum roleEnum = getRoleEnum(authCreateUserRequest);

        // Encontrar el rol correspondiente
        RoleEntity roleEntity = roleRepository.findByRoleEnum(roleEnum)
                .orElseThrow(() -> new IllegalArgumentException("El rol especificado no existe"));

        // Crear el usuario
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .email(email) // Asignar email
                .password(passwordEncoder.encode(password))
                .role(roleEntity) // Asignar un solo rol
                .isEnable(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();

        // Guardamos el usuario
        UserEntity userCreated = userRepository.save(userEntity);

        // Creamos autoridad
        List<SimpleGrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority("ROLE_" + userCreated.getRole().getRoleEnum().name()));

        // Se establecec el contexto de seguridad
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(), userCreated.getPassword(), authorityList);

        // Se crea el tokenJWT
        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponse(userCreated.getUsername(), "Usuario creado con éxito", accessToken, true);
    }

    private RoleEnum getRoleEnum(AuthCreateUserRequest authCreateUserRequest) {
        RoleEnum roleEnum = authCreateUserRequest.roleEnum(); // Recibimos un solo rol

        //Logica para crear los usuarios
        // Si el rol no está especificado, asignamos "CLIENTE" por defecto
        if (roleEnum == null) {
            roleEnum = RoleEnum.CLIENT;
        }

        // Si el rol es ADMIN o AGENT, se debe verificar que el usuario autenticado sea ADMIN
        if (roleEnum == RoleEnum.ADMIN || roleEnum == RoleEnum.AGENT) {
            if (!isAdminUser()) {
                throw new IllegalArgumentException("Solo un administrador puede asignar roles de ADMIN o AGENT");
            }
        }
        return roleEnum;
    }

}

