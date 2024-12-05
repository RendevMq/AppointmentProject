package com.appointment.service.interfaces;

import com.appointment.presentation.dto.AppointmentCreateRequestDto;
import com.appointment.presentation.dto.AppointmentDto;

import java.util.List;

public interface IAppointmentService {

    void createAppointment(AppointmentCreateRequestDto appointmentRequest);

    List<AppointmentDto> getAppointmentsByUserId(Long userId);

    void completeAppointment(Long appointmentId);

    void reopenAppointment(Long appointmentId);

    // Obtener citas pendientes
    List<AppointmentDto> getAllPendingAppointments();
}

