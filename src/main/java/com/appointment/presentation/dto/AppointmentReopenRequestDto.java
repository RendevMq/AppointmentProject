package com.appointment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO para Reabrir Cita

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentReopenRequestDto {

    private Long appointmentId;
}
