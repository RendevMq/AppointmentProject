package com.appointment.persistence.entity;

public enum AppointmentStatus {
    PENDING,       // La cita está pendiente, esperando asignación
    ASSIGNED,      // La cita ha sido asignada a un agente
    COMPLETED,     // La cita ha sido completada por el agente
    PENDING_REOPENED       // La cita ha sido reabierta después de ser completada
}
