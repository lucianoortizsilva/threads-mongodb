package com.lucianoortizsilva.movies.schedule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import com.lucianoortizsilva.movies.data.Extraction;
import com.lucianoortizsilva.movies.data.Transformation;
import com.lucianoortizsilva.movies.dto.MovieDTO;
import com.lucianoortizsilva.movies.dto.Platform;
import com.lucianoortizsilva.movies.repository.MovieRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Configuration
public class Schedule {

	private static final long FIVE_MINUTE = 320000;

	@Autowired
	private MovieRepository movieRepository;

	private ExecutorService executorService = Executors.newFixedThreadPool(3);

	@Async
	@Scheduled(fixedRate = FIVE_MINUTE)
	public void toProcess() throws FileNotFoundException, IOException, InterruptedException, ExecutionException {

		executorService.submit(() -> {
			toProcessAmazonPrime();
		});

		executorService.submit(() -> {
			toProcessNetflix();
		});

		executorService.submit(() -> {
			toProcessDisney();
		});
	}

	private void toProcessDisney() {
		log.info(">>> Starting Diney");
		try (var extraction = new Extraction("data_disney.csv", ",")) {
			final Transformation transform = new Transformation(Platform.DISNEY, extraction.getData(), 20000);
			final List<MovieDTO> dtos = transform.getMovies().stream().collect(Collectors.toList());
			dtos.forEach(dto -> {
				movieRepository.save(dto.ToEntity());
			});
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			log.info(">>> Finished Disney");
		}
	}

	private void toProcessAmazonPrime() {
		log.info(">>> Starting Amazon Prime");
		try (var extraction = new Extraction("data_amazon_prime.csv", ",")) {
			final Transformation transform = new Transformation(Platform.AMAZON_PRIME, extraction.getData(), 1);
			final List<MovieDTO> dtos = transform.getMovies().stream().collect(Collectors.toList());
			dtos.forEach(dto -> {
				movieRepository.save(dto.ToEntity());
			});
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			log.info(">>> Finished Amazon Prime");
		}
	}

	private void toProcessNetflix() {
		log.info(">>> Starting Netflix");
		try (var extraction = new Extraction("data_netflix.csv", ",")) {
			final Transformation transform = new Transformation(Platform.NETFLIX, extraction.getData(), 10000);
			final List<MovieDTO> dtos = transform.getMovies().stream().collect(Collectors.toList());
			dtos.forEach(dto -> {
				movieRepository.save(dto.ToEntity());
			});
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			log.info(">>> Finished Netflix");
		}
	}

}