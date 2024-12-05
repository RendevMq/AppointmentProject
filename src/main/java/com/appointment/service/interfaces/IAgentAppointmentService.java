package com.appointment.service.interfaces;

import com.appointment.presentation.dto.AppointmentDto;

import java.util.List;

public interface IAgentAppointmentService {

    void attendAppointment(Long appointmentId);

    List<AppointmentDto> getAssignedAppointments();
}
