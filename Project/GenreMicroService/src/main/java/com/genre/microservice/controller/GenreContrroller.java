package com.genre.microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genre.microservice.model.Genre;
import com.genre.microservice.service.GenreService;

@RestController
@RequestMapping("/genre")
public class GenreContrroller {
	@Autowired
	private GenreService genreService;
	
	@GetMapping("/all")
	List<Genre> getAllGenres(){
		List<Genre> allGenres = genreService.getAllGenres();
		
		return allGenres;
	}
	
	@GetMapping("/genremovie/{id}")
	Genre getGenreById(@PathVariable("id") Long id) {
		Genre genreById = genreService.getGenreById(id);
		return genreById;
	}

}
