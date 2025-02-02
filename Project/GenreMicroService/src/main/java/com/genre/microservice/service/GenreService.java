package com.genre.microservice.service;

import java.util.List;

import com.genre.microservice.model.Genre;

public interface GenreService {
	
	List<Genre> getAllGenres();
	
	Genre getGenreById(Long id);

}
