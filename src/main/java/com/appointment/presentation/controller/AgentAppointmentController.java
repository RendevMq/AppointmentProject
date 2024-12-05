package com.appointment.presentation.controller;

import com.appointment.presentation.dto.AppointmentDto;
import com.appointment.service.interfaces.IAgentAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments/agent")
public class AgentAppointmentController {

    @Autowired
    private IAgentAppointmentService agentAppointmentService;

    // Asistir a una cita
    @PostMapping("/{appointmentId}/attend")
    public ResponseEntity<AppointmentDto> attendAppointment(@PathVariable Long appointmentId) {
        agentAppointmentService.attendAppointment(appointmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
