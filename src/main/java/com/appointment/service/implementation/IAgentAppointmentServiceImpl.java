package com.appointment.service.implementation;

import com.appointment.persistence.entity.AppointmentEntity;
import com.appointment.persistence.entity.AppointmentStatus;
import com.appointment.persistence.repository.AppointmentRepository;
import com.appointment.service.interfaces.IAgentAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IAgentAppointmentServiceImpl implements IAgentAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void attendAppointment(Long appointmentId) {
        Optional<AppointmentEntity> appointmentEntityOptional = appointmentRepository.findById(appointmentId);

        if (appointmentEntityOptional.isEmpty()) {
            throw new RuntimeException("Appointment not found");
        }

        AppointmentEntity appointmentEntity = appointmentEntityOptional.get();

        if (appointmentEntity.getStatus() != AppointmentStatus.ASSIGNED) {
            throw new RuntimeException("Appointment is not in ASSIGNED status");
        }

        // Actualizamos la cita a COMPLETED
        appointmentEntity.setStatus(AppointmentStatus.COMPLETED);
        appointmentEntity.setCompletedDate(appointmentEntity.getCompletedDate() == null ? java.time.LocalDateTime.now() : appointmentEntity.getCompletedDate());

        appointmentRepository.save(appointmentEntity);
    }
}
