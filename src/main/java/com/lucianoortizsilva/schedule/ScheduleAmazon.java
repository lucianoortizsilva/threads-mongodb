package com.lucianoortizsilva.schedule;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.lucianoortizsilva.csv.ReadCSV;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Configuration
@EnableScheduling
public class ScheduleAmazon {

	@Async
	@Scheduled(fixedRate = 600000)
	public void toProcess() throws FileNotFoundException, IOException {
		log.info("---------AMAZON----------");
		try (var readFile = new ReadCSV("data_amazon_prime.csv", ",")) {
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			log.info("---------AMAZON----------\n");
		}
	}

}