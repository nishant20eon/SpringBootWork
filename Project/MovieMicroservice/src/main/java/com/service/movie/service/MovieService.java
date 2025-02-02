package com.service.movie.service;

import java.util.List;

import com.service.movie.model.Movie;

public interface MovieService {

	List<Movie> getAllMovies();
	
	List<Movie> getMovieByGenreId(Long genreId);
}
