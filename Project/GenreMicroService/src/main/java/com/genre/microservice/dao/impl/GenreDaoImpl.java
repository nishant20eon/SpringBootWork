package com.genre.microservice.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.genre.microservice.dao.GenreDAO;
import com.genre.microservice.model.Genre;

@Repository
public class GenreDaoImpl implements GenreDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Genre> getAllGenres() {
		// TODO Auto-generated method stub
	
		 List<Genre> genereList = jdbcTemplate.query("SELECT * FROM genre", BeanPropertyRowMapper.newInstance(Genre.class));
			System.out.println("************ genereList: "+genereList);
		 return genereList;
	}

	@Override
	public Genre getGenreById(Long id) {
		return jdbcTemplate.queryForObject("select * from genre where id=?", 
				BeanPropertyRowMapper.newInstance(Genre.class),id);
		}

}
