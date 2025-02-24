package com.vtech.ecommerce.audit.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vtech.ecommerce.audit.model.AuditLog;
import com.vtech.ecommerce.audit.repository.AuditLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(transactionManager = "auditTransactionManager")
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {
	
	private final AuditLogRepository auditLogRepository;

	@Override
	public void logAction(String action, String entity, String detail) {
		AuditLog auditLog = AuditLog.builder()
				.action(action)
				.entity(entity)
				.detail(detail)
				.build();
		auditLogRepository.save(auditLog);
	}

}
