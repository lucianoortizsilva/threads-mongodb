package com.lucianoortizsilva.movies.repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.lucianoortizsilva.movies.model.Movie;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class MoveSaveThread extends Thread {

	@Setter
	private MovieRepository movieRepository;
	@Setter
	private List<Movie> movies;
	@Setter
	private int firstIndex = 0;
	@Setter
	private int lastIndex = 0;

	@Override
	public void run() {
		log.info("CurrentThread: [{}] - Lines range: [{}]-[{}]", Thread.currentThread().getName(), (firstIndex + 1), lastIndex);
		final List<Movie> list = IntStream.range(firstIndex, lastIndex).mapToObj(i -> movies.get((i))).collect(Collectors.toList());
		this.movieRepository.saveAll(list);
	}

}