package com.lucianoortizsilva.movies.dto;

import com.lucianoortizsilva.movies.model.Movie;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDTO {

	private String id;
	private String type;
	private String title;
	private String director;
	private String cast;
	private String country;
	private String dtAdded;
	private String releaseYear;
	private String rating;
	private String duration;
	private String listedIn;
	private String description;
	private Platform platform;

	public Movie ToEntity() {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setType(type);
		movie.setTitle(title);
		movie.setDirector(director);
		movie.setCast(cast);
		movie.setCountry(country);
		movie.setDtAdded(dtAdded);
		movie.setReleaseYear(releaseYear);
		movie.setRating(rating);
		movie.setDuration(duration);
		movie.setListedIn(listedIn);
		movie.setDescription(description);
		movie.setPlatform(platform.name());
		return movie;
	}

}