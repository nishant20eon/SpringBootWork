package com.genre.microservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genre.microservice.dao.GenreDAO;
import com.genre.microservice.model.Genre;
import com.genre.microservice.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService{

	@Autowired
	private GenreDAO genredao;
	
	@Override
	public List<Genre> getAllGenres() {
		// TODO Auto-generated method stub
		return genredao.getAllGenres();
	}

	@Override
	public Genre getGenreById(Long id) {
		// TODO Auto-generated method stub
		return genredao.getGenreById(id);
	}

}
