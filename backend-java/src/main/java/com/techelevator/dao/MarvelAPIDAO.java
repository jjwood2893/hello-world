package com.techelevator.dao;

import java.util.List;
import java.util.Map;

import com.techelevator.model.Comic;
import com.techelevator.model.User;

public interface MarvelAPIDAO {

	Map<String,Object> getComic(String upc);
	
	List<Comic> unpackNested(Map<String,Object> results);
	
	
	
}
