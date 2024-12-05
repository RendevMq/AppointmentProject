package com.appointment.service.interfaces;

import com.appointment.presentation.dto.AppointmentCreateRequestDto;
import com.appointment.presentation.dto.AppointmentDto;

import java.util.List;

public interface IClientAppointmentService {

    // Crear una nueva cita
    AppointmentDto createAppointment(AppointmentCreateRequestDto appointmentRequestDto);

    // Obtener todas las citas del cliente autenticado
    List<AppointmentDto> getAppointmentsByClientId(Long clientId);

    // Obtener los detalles de una cita específica por su ID
    AppointmentDto getAppointmentDetails(Long appointmentId, Long clientId);
}
