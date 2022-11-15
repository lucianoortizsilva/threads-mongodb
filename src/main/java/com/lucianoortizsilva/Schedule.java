package com.lucianoortizsilva;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Schedule {

	@Scheduled(fixedRate = 10000)
	public void x() {
		System.out.println("Hello World!");
	}

}