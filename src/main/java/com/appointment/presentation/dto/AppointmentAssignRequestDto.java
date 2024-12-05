package com.appointment.presentation.dto;

/*Este DTO se utiliza cuando el administrador asigna una cita a un agente.
Contiene los datos del agente y la cita que se debe asignar.*/

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentAssignRequestDto {

    @NotNull
    private Long agentId; // ID del agente al que se asignará la cita

    @NotNull
    private Long appointmentId; // ID de la cita que se va a asignar
}
