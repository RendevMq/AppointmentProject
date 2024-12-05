package com.appointment.persistence.repository;

import com.appointment.persistence.entity.AuditEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends CrudRepository<AuditEntity, Long> {

}
