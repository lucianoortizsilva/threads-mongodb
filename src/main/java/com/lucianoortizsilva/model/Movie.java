package com.lucianoortizsilva.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movie {

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

}