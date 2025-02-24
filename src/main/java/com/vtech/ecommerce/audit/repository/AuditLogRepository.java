package com.vtech.ecommerce.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtech.ecommerce.audit.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
