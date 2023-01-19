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

	public Movie ToEntity() {
		Movie movie = new Movie();
		movie.setId(Integer.parseInt(id));
		movie.setType(type);
		movie.setTitle(title);
		movie.setDirector(director);
		movie.setCast(cast);
		movie.setCountry(country.isEmpty() ? "Undefined" : country);
		movie.setDtAdded(dtAdded);
		movie.setRating(rating);
		movie.setDuration(duration);
		movie.setListedIn(listedIn);
		movie.setDescription(description);
		try {
			movie.setReleaseYear(Integer.valueOf(releaseYear.trim()));
		} catch (Exception e) {
			movie.setReleaseYear(null);
		}
		return movie;
	}

}