package com.lucianoortizsilva.schedule;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.lucianoortizsilva.csv.ReadCSV;
import com.lucianoortizsilva.model.Platform;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Configuration
@EnableScheduling
public class ScheduleAmazon {

	private static final long ONE_MINUTE = 60000;

	@Async
	@Scheduled(fixedRate = ONE_MINUTE)
	public void toProcess() throws FileNotFoundException, IOException {
		log.info("---------AMAZON----------");
		try (var readFile = new ReadCSV(Platform.AMAZON_PRIME, "data_amazon_prime.csv", ",")) {
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			log.info("---------AMAZON----------\n");
		}
	}

}