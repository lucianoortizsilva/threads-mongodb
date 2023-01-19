package com.lucianoortizsilva.movies.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Movies")
public class Movie {

	@Id
	private Integer id;
	private String type;
	private String title;
	private String director;
	private String cast;
	private String country;
	private String dtAdded;
	private Integer releaseYear;
	private String rating;
	private String duration;
	private String listedIn;
	private String description;

}