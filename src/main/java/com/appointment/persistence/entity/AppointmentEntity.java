package com.appointment.persistence.entity;


import com.appointment.persistence.entity.authEntities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class AppointmentEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentDate; // Fecha de la cita
    private String project; // Proyecto relacionado con la cita
    private String consultation; // Tema de la cita

    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserEntity client; // Cliente que creó la cita

    @ManyToOne
    @JoinColumn(name = "assigned_agent_id")
    private UserEntity assignedAgent; // Agente asignado

    private LocalDateTime assignmentDate; // Fecha de asignación al agente
    private LocalDateTime completedDate; // Fecha cuando la cita fue completada
    private AppointmentStatus status; // Estado de la cita

    private LocalDateTime reopenedDate; // Fecha cuando la cita fue reabierta
}

