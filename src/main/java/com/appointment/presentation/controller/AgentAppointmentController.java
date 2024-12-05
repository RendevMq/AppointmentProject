package com.appointment.presentation.controller;

import com.appointment.presentation.dto.AppointmentDto;
import com.appointment.service.interfaces.IAgentAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments/agent")
public class AgentAppointmentController {

    @Autowired
    private IAgentAppointmentService agentAppointmentService;

    // Ver todas las citas asignadas al agente
    @GetMapping("/assigned")
    public ResponseEntity<List<AppointmentDto>> getAssignedAppointments() {
        List<AppointmentDto> appointments = agentAppointmentService.getAssignedAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Asistir a una cita
    @PostMapping("/{appointmentId}/attend")
    public ResponseEntity<AppointmentDto> attendAppointment(@PathVariable Long appointmentId) {
        agentAppointmentService.attendAppointment(appointmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
