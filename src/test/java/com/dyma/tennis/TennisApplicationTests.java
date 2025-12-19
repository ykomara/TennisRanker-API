package com.dyma.tennis;

import com.dyma.tennis.data.HealthCheckRepository;
import com.dyma.tennis.web.HealthCheckController;
import com.dyma.tennis.service.HealthCheckService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TennisApplicationTests {

	@Autowired
	private HealthCheckController healthCheckController;
	@Autowired
	private HealthCheckRepository healthCheckRepository;
	@Autowired
	private HealthCheckService healthCheckService;

	@Test
	void contextLoads() {
		assert (healthCheckController != null);
		assert (healthCheckRepository != null);
		assert (healthCheckService != null);
	}

}
