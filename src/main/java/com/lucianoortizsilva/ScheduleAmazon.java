package com.lucianoortizsilva;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Configuration
@EnableScheduling
public class ScheduleAmazon {

	@Async
	@Scheduled(fixedRate = 10000)
	public void toProcess() {
		log.info("----------AMAZON----------");
	}

}