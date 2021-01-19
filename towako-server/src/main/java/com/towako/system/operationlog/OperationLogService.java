package com.towako.system.operationlog;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author colin
 */
@Service
public class OperationLogService {
    private final OperationLogRepository repository;

    public OperationLogService(OperationLogRepository repository) {
        this.repository = repository;
    }

    @Transactional(rollbackOn = Exception.class)
    public void createOperationLog(OperationLogType type, Long userId, String ip, String url,
                                   String method, String parameters, Boolean isSuccess) {
        repository.save(new OperationLog(type, userId, ip, url, method, parameters, isSuccess));
    }
}
