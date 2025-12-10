package com.dyma.tennis.service;

import com.dyma.tennis.ApplicationStatus;
import com.dyma.tennis.HealthCheck;
import com.dyma.tennis.repository.HealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    @Autowired
    private HealthCheckRepository healthCheckRepository;

    public HealthCheck healthCheck(){
        Long activeSessions = healthCheckRepository.countApplicationConnections();
        if(activeSessions > 0){
            return new HealthCheck(ApplicationStatus.OK, "Database connections active: " + activeSessions);
        }
        return new HealthCheck(ApplicationStatus.KO, "No active database connections");
    }
}
