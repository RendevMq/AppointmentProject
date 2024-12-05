package com.appointment.presentation.dto;

public enum AppointmentStatus {
    PENDING,       // La cita está pendiente, esperando asignación
    ASSIGNED,      // La cita ha sido asignada a un agente
    COMPLETED,     // La cita ha sido completada por el agente
    REOPENED       // La cita ha sido reabierta después de ser completada
}
