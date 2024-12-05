package com.appointment.service.interfaces;

import com.appointment.presentation.dto.AppointmentDto;

import java.util.List;

public interface IAdminAppointmentService {

    void assignAppointmentToAgent(Long appointmentId, Long agentId);

    List<AppointmentDto> getAllPendingAppointments();
}

