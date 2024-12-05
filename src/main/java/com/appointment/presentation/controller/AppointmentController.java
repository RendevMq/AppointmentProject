package com.appointment.presentation.controller;

import com.appointment.presentation.dto.AppointmentCreateRequestDto;
import com.appointment.presentation.dto.AppointmentDto;
import com.appointment.service.interfaces.IClientAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments/client")
public class AppointmentController {

    @Autowired
    private IClientAppointmentService clientAppointmentService;

    // Crear una nueva cita
    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentCreateRequestDto appointmentCreateRequest) {
        AppointmentDto appointment = clientAppointmentService.createAppointment(appointmentCreateRequest);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    // Obtener todas las citas del cliente autenticado
    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByClient() {
        List<AppointmentDto> appointments = clientAppointmentService.getAppointmentsByAuthenticatedClient();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Obtener los detalles de una cita específica del cliente autenticado
    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDto> getAppointmentDetails(@PathVariable Long appointmentId) {
        AppointmentDto appointment = clientAppointmentService.getAppointmentDetails(appointmentId);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }
}
