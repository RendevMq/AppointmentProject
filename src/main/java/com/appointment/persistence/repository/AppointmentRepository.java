package com.appointment.persistence.repository;

import com.appointment.persistence.entity.AppointmentEntity;
import com.appointment.persistence.entity.AppointmentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends CrudRepository<AppointmentEntity, Long> {

    // Encontrar citas por su cliente (ID)
    List<AppointmentEntity> findByClient_Id(Long clientId);

    // Encontrar todas las citas con un estado específico
    List<AppointmentEntity> findByStatus(AppointmentStatus status);

    // Encontrar una cita por su ID
    Optional<AppointmentEntity> findById(Long id);

    // Encontrar todas las citas pendientes
    List<AppointmentEntity> findByStatusAndAssignedAgentIsNull(AppointmentStatus status);

    // Encontrar citas asignadas a un agente específico
    List<AppointmentEntity> findByAssignedAgent_Id(Long agentId);
}
