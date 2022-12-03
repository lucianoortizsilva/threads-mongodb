package com.lucianoortizsilva.movies.data;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.lucianoortizsilva.movies.dto.MovieDTO;
import com.lucianoortizsilva.movies.dto.Platform;

import lombok.Getter;

public class Transformation {

	private Platform platform;
	private List<String> data;
	private int initialId;

	@Getter
	private Collection<MovieDTO> movies;

	public Transformation(final Platform platform, final List<String> data, final int initialId) {
		Objects.nonNull(platform);
		Objects.nonNull(data);
		this.initialId = initialId;
		this.platform = platform;
		this.data = data;
		this.toTransform();
	}

	private void toTransform() {
		this.movies = new LinkedList<>();
		for (final String data : this.data) {
			final var line = data.split(";");
			final var lineLength = line.length;
			final MovieDTO movie = MovieDTO.builder()//
					.id(String.valueOf(initialId))//
					.type(lineLength >= 1 ? line[1] : "")//
					.title(lineLength >= 2 ? line[2] : "")//
					.director(lineLength >= 3 ? line[3] : "")//
					.cast(lineLength >= 4 ? line[4] : "")//
					.country(lineLength >= 5 ? line[5] : "")//
					.dtAdded(lineLength >= 6 ? line[6] : "")//
					.releaseYear(lineLength >= 7 ? line[7] : "")//
					.rating(lineLength >= 8 ? line[8] : "")//
					.duration(lineLength >= 9 ? line[9] : "")//
					.listedIn(lineLength >= 10 ? line[10] : "")//
					.description(lineLength >= 11 ? line[11] : "")//
					.platform(this.platform).build();//
			this.movies.add(movie);
			initialId++;
		}
	}

}