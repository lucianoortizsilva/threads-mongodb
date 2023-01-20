package com.lucianoortizsilva.movies.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.lucianoortizsilva.movies.model.Movie;

public interface MovieRepository extends MongoRepository<Movie, String> {

	@Query("{title:'?0'}")
	Movie findByTitle(String title);

	public long count();

}