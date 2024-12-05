package com.appointment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO para Completar Cita

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentCompleteRequestDto {

    private Long appointmentId;
}
