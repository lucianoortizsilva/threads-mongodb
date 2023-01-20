package com.lucianoortizsilva.movies.schedule;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.lucianoortizsilva.movies.data.Extraction;
import com.lucianoortizsilva.movies.data.Transformation;
import com.lucianoortizsilva.movies.dto.MovieDTO;
import com.lucianoortizsilva.movies.model.Movie;
import com.lucianoortizsilva.movies.repository.MoveSaveThread;
import com.lucianoortizsilva.movies.repository.MovieRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Configuration
public class Schedule {

	@Autowired
	private MovieRepository movieRepository;

	private static final long FIVE_MINUTE = 320000;
	private static final long TEN_MINUTE = FIVE_MINUTE * 2;
	private static final long ONE_HOUR = TEN_MINUTE * 6;
	private static final String[] CSV_ALL = { "1.csv", "2.csv", "3.csv", "4.csv" };

	private int QTD_THREADS = 4;

	@Async
	@Transactional
	@Scheduled(fixedRate = ONE_HOUR)
	public void toProcess() {
		try {
			final Transformation transformation = readAndTransform();
			if (nonNull(transformation)) {
				final List<MovieDTO> dtos = transformation.getMovies().stream().collect(Collectors.toList());
				final List<Movie> movies = dtos.stream().map(dto -> dto.ToEntity()).collect(Collectors.toUnmodifiableList());
				final List<Thread> allThreads = new ArrayList<>();
				this.createTasks(allThreads, movies);
				initTasks(allThreads);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private static void initTasks(List<Thread> threads) throws InterruptedException {
		long init = System.currentTimeMillis();
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
		long end = System.currentTimeMillis() - init;
		log.info("Timel total: {}", end);
	}

	private void createTasks(final List<Thread> allThreads, final List<Movie> movies) {
		int countTasks = 1;
		int firstIndex = 0;
		int quantityByThread = movies.size() / QTD_THREADS;
		int lastIndeX = quantityByThread;
		boolean isLast = false;

		for (int i = 1; i <= QTD_THREADS; i++) {
			if (countTasks == QTD_THREADS) {
				isLast = true;
			}
			if (isLast) {
				int diff = movies.size() - lastIndeX;
				lastIndeX = lastIndeX + diff;
			}
			MoveSaveThread moveSaveThread = new MoveSaveThread();
			moveSaveThread.setMovies(movies);
			moveSaveThread.setFirstIndex(firstIndex);
			moveSaveThread.setLastIndex(lastIndeX);
			moveSaveThread.setName("THREAD NAME: " + i);
			moveSaveThread.setMovieRepository(movieRepository);
			allThreads.add(moveSaveThread);
			firstIndex = lastIndeX;
			lastIndeX += quantityByThread;
			countTasks++;
		}
	}

	private static Transformation readAndTransform() {
		try (var extraction = new Extraction(CSV_ALL, ",")) {
			final int initialId = 1;
			return new Transformation(extraction.getData(), initialId);
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}