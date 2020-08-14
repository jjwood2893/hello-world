package com.techelevator.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Comic {
	   private Long comicId;
		
	   private String title;
		
	   private Long issue; 
		
	   private String author;
		
	   private String artist;
	   private String publisher;
	   
	   private String coverUrl;
	   private List<ComicCharacter> characters;
	
	 
	   
	    public Comic() {
		// TODO Auto-generated constructor stub
	    }

	   public Comic(Long comicId, String title, Long issue, String author, String artist, String publisher, List<ComicCharacter> characters) {
		   this.setComicId(comicId);
		   this.setTitle(title);
		   this.setIssue(issue); 
		   this.setAuthor(author);
		   this.setArtist(artist);
		   this.setPublisher(publisher);
		   this.setCharacters(characters);
		       
	   
	   }

	public Long getComicId() {
		return comicId;
	}

	public void setComicId(Long comicId) {
		this.comicId = comicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getIssue() {
		return issue;
	}

	public void setIssue(Long issue) {
		this.issue = issue;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public List<ComicCharacter> getCharacters() {
		return characters;
	}

	public void setCharacters(List<ComicCharacter> characters) {
		this.characters = characters;
	}
	
	
	@Override
	public String toString() {
		String comicString = "title = "+this.title+
							"/n issue = "+this.issue+
							"/n author = "+this.author+
							"/n artist = "+this.artist;
		return comicString;
		
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
}
