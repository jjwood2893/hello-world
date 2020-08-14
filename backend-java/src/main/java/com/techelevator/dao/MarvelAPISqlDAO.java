package com.techelevator.dao;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Collection;
import com.techelevator.model.Comic;
import com.techelevator.model.ComicCharacter;
import com.techelevator.model.User;

@Service
public class MarvelAPISqlDAO implements MarvelAPIDAO{

	private JdbcTemplate jdbcTemplate;
	private UserDAO userDAO;
	private RestTemplate restTemplate = new RestTemplate();
	private final String IMAGE_EXTENSION = "/portrait_uncanny.jpg";

	public MarvelAPISqlDAO(UserDAO userDAO, JdbcTemplate jdbcTemplate) {
		this.userDAO = userDAO;
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getComic(String upc) {
		ResponseEntity<String> response = restTemplate.getForEntity("https://gateway.marvel.com/v1/public/comics?apikey=9b2a0b50935e208ae26eb35665dffc5b&ts=1&hash=c32fef8c4f7a7d830777bf84df88b0df&upc=" + upc, 
		                String.class); // Make GET request using Client
		String jsonResult = response.getBody();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> results = null;
		try {
			results = mapper.readValue(jsonResult, Map.class);
			System.out.println("WEDIDIT");
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
		
	}
	
	@SuppressWarnings("unchecked")
	@JsonProperty("data")
	public List<Comic> unpackNested(Map<String,Object> results) {
		Map<String, Object> data = (Map<String,Object>) results.get("data");
		List<Object> comics = (List<Object>) data.get("results");
		List<Comic> parsedComics = new ArrayList<Comic>();
		for (Object comic: comics) {
			Comic newComic = new Comic();
			newComic.setPublisher("Marvel");
			Map<String, Object> comicMap = (Map<String, Object>) comic;
			newComic.setTitle((String) comicMap.get("title"));
			Integer issueNumber = (Integer) comicMap.get("issueNumber");
			newComic.setIssue(issueNumber.longValue());
			List<Object> images = (List<Object>)comicMap.get("images");
			Map<String,String> coverMap = (Map<String,String>)images.get(0);
			newComic.setCoverUrl(coverMap.get("path")+IMAGE_EXTENSION);
			Map<String, Object> creators = (Map<String,Object>)comicMap.get("creators");
			List<Object> creatorsFullMap = (List<Object>)creators.get("items");
			for (Object creator:creatorsFullMap) {
				Map<String,String> creatorMap = (LinkedHashMap<String,String>) creator;
				if ((creatorMap.get("role").contains("penciller")||creatorMap.get("role").contains("inker") || creatorMap.get("role").contains("penciler") )&& newComic.getArtist() == null) {
					newComic.setArtist((String) creatorMap.get("name"));
				}
				if (creatorMap.get("role").matches("writer") && newComic.getAuthor() == null) {
					newComic.setAuthor((String) creatorMap.get("name"));
				}
			}
			Map<String,Object> characters = (Map<String,Object>)comicMap.get("characters");
			List<Object> charactersFullMap = (List<Object>)characters.get("items");
			List<ComicCharacter> charactersInComic = new ArrayList<ComicCharacter>();
			for (Object character:charactersFullMap) {
				Map<String,String> characterMap = (LinkedHashMap<String,String>) character;
				ComicCharacter currentCharacter = new ComicCharacter();
				currentCharacter.setName((String) characterMap.get("name"));
				charactersInComic.add(currentCharacter);
			}
			newComic.setCharacters(charactersInComic);
			parsedComics.add(newComic);
		}
		return parsedComics;
		
	}

}
