package com.appointment.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Long id; // ID de la cita

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appointmentDate; // Fecha de la cita programada

    private String project; // Proyecto relacionado con la cita
    private String consultation; // Tema de la consulta en la cita

    private Long clientId; // ID del cliente que creó la cita
    private String clientName; // Nombre del cliente que creó la cita

    private Long assignedAgentId; // ID del agente al que se le asignó la cita (si está asignada)
    private String assignedAgentName; // Nombre del agente al que se le asignó la cita

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assignmentDate; // Fecha en la que la cita fue asignada a un agente (si está asignada)

    private AppointmentStatus status; // Estado actual de la cita (PENDING, ASSIGNED, COMPLETED, REOPENED)

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedDate; // Fecha cuando la cita fue completada (si fue completada)

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reopenedDate; // Fecha cuando la cita fue reabierta (si fue reabierta)
}
