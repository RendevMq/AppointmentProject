package com.appointment.service.interfaces;

import com.appointment.presentation.dto.AppointmentDto;
import com.appointment.presentation.dto.UserDto;

import java.util.List;

public interface IAdminAppointmentService {

    void assignAppointmentToAgent(Long appointmentId, Long agentId);

    List<AppointmentDto> getAllPendingAppointments();

    // Nuevo  para obtener todas las citas
    List<AppointmentDto> getAllAppointments();

    // Nuevo  para obtener todos los usuarios
    List<UserDto> getAllUsers();

    AppointmentDto reopenAppointment(Long appointmentId);
}

