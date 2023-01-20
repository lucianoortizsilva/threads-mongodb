package com.lucianoortizsilva.movies.data;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.lucianoortizsilva.movies.dto.MovieDTO;

import lombok.Getter;

public class Transformation {

	private List<String> data;
	private int initialId;

	@Getter
	private Collection<MovieDTO> movies;

	public Transformation(final List<String> data, final int initialId) {
		Objects.nonNull(data);
		this.initialId = initialId;
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
					.type(lineLength > 0 ? line[0] : "")//
					.title(lineLength > 1 ? line[1] : "")//
					.director(lineLength > 2 ? line[2] : "")//
					.cast(lineLength > 3 ? line[3] : "")//
					.country(lineLength > 4 ? line[4] : "")//
					.dtAdded(lineLength > 5 ? line[5] : "")//
					.releaseYear(lineLength > 6 ? line[6] : "")//
					.rating(lineLength > 7 ? line[7] : "")//
					.duration(lineLength > 8 ? line[8] : "")//
					.listedIn(lineLength > 9 ? line[9] : "")//
					.description(lineLength > 10 ? line[10] : "").build();//
			this.movies.add(movie);
			initialId++;
		}
	}

}