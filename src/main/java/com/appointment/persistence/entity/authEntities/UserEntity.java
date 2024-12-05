package com.appointment.persistence.entity.authEntities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    @Column(name = "is_enable")
    private boolean isEnable;

    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;

    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;

    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;

    // Relación a @ManyToOne porque cada usuario tiene un único rol
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id") // Foreign Key en la tabla users
    private RoleEntity role;  // Cada usuario tiene un solo rol
}
