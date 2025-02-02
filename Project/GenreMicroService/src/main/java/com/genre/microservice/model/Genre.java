package com.genre.microservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Genre {
	
	private Long id;
	private String name;
	
	List<Movie> movieList = new ArrayList<>();
	
	public List<Movie> getMovieList() {
		return movieList;
	}

	public void setMovieList(List<Movie> movieList) {
		this.movieList = movieList;
	}

	public Genre() {
		
	}

	public Genre(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
//		this.movieList = movieList;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Genre [id=" + id + ", name=" + name + ", movieList=" + movieList + "]";
	}
	
}
