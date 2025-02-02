package com.service.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.movie.model.Movie;
import com.service.movie.service.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping("/all")
	List<Movie> getAllMoovies() {
		return movieService.getAllMovies();
	}
	
	@GetMapping("/genre/{id}")
	public List<Movie> getMovieByGenreId(@PathVariable("id") Long genreId) {
		System.out.println("********************  "+genreId);
		return movieService.getMovieByGenreId(genreId);
		
	}

}
