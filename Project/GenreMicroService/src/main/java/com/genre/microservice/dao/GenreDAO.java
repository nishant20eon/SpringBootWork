package com.genre.microservice.dao;

import java.util.List;

import com.genre.microservice.model.Genre;

public interface GenreDAO {
	
	List<Genre> getAllGenres();
	
	Genre getGenreById(Long id);
	
}
