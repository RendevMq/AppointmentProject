package com.appointment.presentation.controller;

import com.appointment.presentation.dto.AppointmentDto;
import com.appointment.presentation.dto.AppointmentAssignRequestDto;
import com.appointment.presentation.dto.AppointmentReopenRequestDto;
import com.appointment.presentation.dto.UserDto;
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
    @PostMapping("/assign")
    public ResponseEntity<AppointmentDto> assignAppointmentToAgent(
            @RequestBody AppointmentAssignRequestDto request) {
        adminAppointmentService.assignAppointmentToAgent(request.getAppointmentId(), request.getAgentId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Obtener todas las citas pendient
    @GetMapping("/pending")
    public ResponseEntity<List<AppointmentDto>> getAllPendingAppointments() {
        List<AppointmentDto> appointments = adminAppointmentService.getAllPendingAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Obtener todas las citas (no solo pendientes)
    @GetMapping("/all")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        List<AppointmentDto> appointments = adminAppointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Obtener todos los usuarios (clientes, agentes, administradores)
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = adminAppointmentService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Reabrir una cita
    @PostMapping("/reopen")
    public ResponseEntity<AppointmentDto> reopenAppointment(
            @RequestBody AppointmentReopenRequestDto request) {
        AppointmentDto appointmentDto = adminAppointmentService.reopenAppointment(request.getAppointmentId());
        return new ResponseEntity<>(appointmentDto, HttpStatus.OK);
    }

}

