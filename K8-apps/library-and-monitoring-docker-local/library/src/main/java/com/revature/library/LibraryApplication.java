package com.revature.library;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
public class LibraryApplication {


	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
		// Spring Boot internal latency formula via prometheus:
		// rate(http_server_requests_seconds_sum{job="library"}[10m]) / rate(http_server_requests_seconds_count{job="library"}[10m])
		// note it does not have to be for 10 minutes, could be 5, 1, an hour, etc
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry){
		return new TimedAspect(registry);
	}

	@Bean
	public CountedAspect countedAspect(MeterRegistry registry){
		return new CountedAspect(registry);
	}

}
