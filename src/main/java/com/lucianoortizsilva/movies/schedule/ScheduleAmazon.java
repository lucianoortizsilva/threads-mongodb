package com.lucianoortizsilva.movies.schedule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import com.lucianoortizsilva.movies.data.Extraction;
import com.lucianoortizsilva.movies.data.Transformation;
import com.lucianoortizsilva.movies.model.Movie;
import com.lucianoortizsilva.movies.model.Platform;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Configuration
public class ScheduleAmazon {

	private static final long ONE_MINUTE = 60000;

	@Async
	@Scheduled(fixedRate = ONE_MINUTE)
	public void toProcess() throws FileNotFoundException, IOException {
		log.info(">>> Starting Amazon Prime");
		try (var extraction = new Extraction("data_amazon_prime.csv", ",")) {
			final Transformation transform = new Transformation(Platform.AMAZON_PRIME, extraction.getData());
			final List<Movie> movies = transform.getMovies().stream().collect(Collectors.toList());
			movies.forEach(movie -> {
				log.info("{}", movie);
			});
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			log.info(">>> Finished Amazon Prime\n");
		}
	}

}
