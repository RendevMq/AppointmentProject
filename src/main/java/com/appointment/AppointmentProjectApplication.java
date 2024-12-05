package com.appointment;

import com.appointment.persistence.entity.authEntities.RoleEntity;
import com.appointment.persistence.entity.authEntities.RoleEnum;
import com.appointment.persistence.entity.authEntities.UserEntity;
import com.appointment.persistence.repository.RoleRepository;
import com.appointment.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class AppointmentProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// 1) Crear los roles
			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.build();
			RoleEntity roleAgent = RoleEntity.builder()
					.roleEnum(RoleEnum.AGENT)
					.build();
			RoleEntity roleClient = RoleEntity.builder()
					.roleEnum(RoleEnum.CLIENT)
					.build();

			// Guardar los roles si no existen
			roleRepository.saveAll(List.of(roleAdmin, roleAgent, roleClient));

			// Recuperar los roles desde la base de datos
			RoleEntity savedRoleAdmin = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
					.orElseThrow(() -> new RuntimeException("Role ADMIN no encontrado"));
			RoleEntity savedRoleAgent = roleRepository.findByRoleEnum(RoleEnum.AGENT)
					.orElseThrow(() -> new RuntimeException("Role AGENT no encontrado"));
			RoleEntity savedRoleClient = roleRepository.findByRoleEnum(RoleEnum.CLIENT)
					.orElseThrow(() -> new RuntimeException("Role CLIENT no encontrado"));

			// 2) Crear los usuarios por defecto (cada uno con un único rol)
			UserEntity adminUser = UserEntity.builder()
					.username("admin")
					.password(passwordEncoder.encode("admin"))  // Contraseña codificada
					.email("admin@example.com")
					.isEnable(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.role(savedRoleAdmin)  // Asignar rol de ADMIN
					.build();

			UserEntity agentUser = UserEntity.builder()
					.username("agent")
					.password(passwordEncoder.encode("agent"))  // Contraseña codificada
					.email("agent@example.com")
					.isEnable(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.role(savedRoleAgent)  // Asignar rol de AGENT
					.build();

			UserEntity clientUser = UserEntity.builder()
					.username("client")
					.password(passwordEncoder.encode("client"))  // Contraseña codificada
					.email("client@example.com")
					.isEnable(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.role(savedRoleClient)  // Asignar rol de CLIENT
					.build();

			// Guardar los usuarios en la base de datos
			userRepository.saveAll(List.of(adminUser, agentUser, clientUser));
		};
	}
}
