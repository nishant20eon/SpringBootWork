package com.service.movie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.movie.DAO.MovieDAO;
import com.service.movie.model.Movie;
import com.service.movie.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService{
	
	@Autowired
	private MovieDAO movieDAO;

	@Override
	public List<Movie> getAllMovies() {
		// TODO Auto-generated method stub
		return movieDAO.getAllMovies();
	}

	@Override
	public List<Movie> getMovieByGenreId(Long genreId) {
		// TODO Auto-generated method stub
		System.out.println("******************** Service "+genreId);
		return movieDAO.getMovieByGenreId(genreId);
	}
	

}
