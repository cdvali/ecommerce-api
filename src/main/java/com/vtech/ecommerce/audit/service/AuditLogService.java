package com.vtech.ecommerce.audit.service;

public interface AuditLogService {
	 void logAction(String action, String entity, String detail);
}
