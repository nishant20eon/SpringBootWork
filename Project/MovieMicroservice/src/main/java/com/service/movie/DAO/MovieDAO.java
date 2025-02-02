package com.service.movie.DAO;

import java.util.List;

import com.service.movie.model.Movie;

public interface MovieDAO {
	
	List<Movie> getAllMovies();
	
	List<Movie> getMovieByGenreId(Long genreId);

}
