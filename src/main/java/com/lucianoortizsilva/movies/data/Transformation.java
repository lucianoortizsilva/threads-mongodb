package com.lucianoortizsilva.movies.data;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.lucianoortizsilva.movies.model.Movie;
import com.lucianoortizsilva.movies.model.Platform;

import lombok.Getter;

public class Transformation {

	private Platform platform;
	private List<String> data;

	@Getter
	private Collection<Movie> movies;

	public Transformation(final Platform platform, final List<String> data) {
		Objects.nonNull(platform);
		Objects.nonNull(data);
		this.platform = platform;
		this.data = data;
		this.toTransform();
	}

	private void toTransform() {
		this.movies = new LinkedList<>();
		for (final String data : this.data) {
			final var line = data.split(";");
			final Movie movie = Movie.builder()//
					.id(line[0])//
					.type(line[1])//
					.title(line[2])//
					.director(line[3])//
					.cast(line[4])//
					.country(line[5])//
					.dtAdded(line[6])//
					.releaseYear(line[7])//
					.rating(line[8])//
					.duration(line[9])//
					.listedIn(line[10])//
					.description(line[11])//
					.platform(this.platform).build();//
			this.movies.add(movie);
		}
	}

}