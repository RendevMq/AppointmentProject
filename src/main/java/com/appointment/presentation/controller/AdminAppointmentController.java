package com.appointment.presentation.controller;

import com.appointment.presentation.dto.AppointmentDto;
import com.appointment.presentation.dto.AppointmentAssignRequestDto;
import com.appointment.service.interfaces.IAdminAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments/admin")
public class AdminAppointmentController {

    @Autowired
    private IAdminAppointmentService adminAppointmentService;

    // Asignar una cita a un agente
    @PostMapping("/{appointmentId}/assign")
    public ResponseEntity<AppointmentDto> assignAppointmentToAgent(
            @PathVariable Long appointmentId, @RequestBody AppointmentAssignRequestDto request) {
        adminAppointmentService.assignAppointmentToAgent(appointmentId, request.getAgentId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Obtener todas las citas pendientes
    @GetMapping("/pending")
    public ResponseEntity<List<AppointmentDto>> getAllPendingAppointments() {
        List<AppointmentDto> appointments = adminAppointmentService.getAllPendingAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}

