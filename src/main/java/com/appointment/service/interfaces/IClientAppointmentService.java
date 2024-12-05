package com.appointment.service.interfaces;

import com.appointment.presentation.dto.AppointmentCreateRequestDto;
import com.appointment.presentation.dto.AppointmentDto;

import java.util.List;

public interface IClientAppointmentService {

    // Crear una nueva cita
    AppointmentDto createAppointment(AppointmentCreateRequestDto appointmentRequestDto);

    // Obtener todas las citas del cliente autenticado
    List<AppointmentDto> getAppointmentsByAuthenticatedClient();

    // Obtener los detalles de una cita específica del cliente autenticado
    AppointmentDto getAppointmentDetails(Long appointmentId);
}
