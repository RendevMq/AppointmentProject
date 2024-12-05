package com.appointment.presentation.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*Este DTO es utilizado cuando un cliente crea una cita. Contendra los datos necesarios para registrar
una nueva cita, como la fecha, el proyecto y la consulta.*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentCreateRequestDto {

    @NotNull
    private LocalDateTime appointmentDate; // Fecha de la cita

    @NotBlank
    private String project; // Proyecto relacionado con la cita

    @NotBlank
    private String query; // Consulta o tema a tratar

    //    @NotNull
    //    private Long userId; // ID del cliente que crea la cita
}
