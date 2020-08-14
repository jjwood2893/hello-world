package com.techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.techelevator.model.ComicCharacter;

@Service
public class CharacterSqlDAO implements CharacterDAO {
	
	private JdbcTemplate jdbcTemplate;
	private UserDAO userDAO;
	private CollectionDAO collectionDAO;

	public CharacterSqlDAO(JdbcTemplate jdbcTemplate, UserDAO userDAO, CollectionDAO collectionDAO) {
		this.userDAO = userDAO;
		this.jdbcTemplate = jdbcTemplate;
		this.collectionDAO = collectionDAO;
	}

	@Override
	public ComicCharacter getCharacterIdByName(String characterName) {
		
		return null;
	}

}
