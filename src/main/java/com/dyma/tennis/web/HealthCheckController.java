package com.dyma.tennis.web;

import com.dyma.tennis.HealthCheck;
import com.dyma.tennis.service.HealthCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "HealthCheck API")
@RestController
public class HealthCheckController {
    @Autowired
    private HealthCheckService healthCheckService;

    @Operation (summary = "Get application health status")
    @ApiResponse(responseCode = "200", description = "Health status retrieved successfully")
    @GetMapping("/healthcheck")
    public HealthCheck healthCheck(){
        return healthCheckService.healthCheck();
    }
}
