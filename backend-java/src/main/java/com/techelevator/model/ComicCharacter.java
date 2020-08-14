package com.techelevator.model;

public class ComicCharacter {
	
	private int characterId;
	private String name;
	
	public ComicCharacter() {
		
	}
	
	public ComicCharacter(int characterId, String name) {
		super();
		this.characterId = characterId;
		this.name = name;
	}
	public int getCharacterId() {
		return characterId;
	}
	public void setCharacterId(int characterId) {
		this.characterId = characterId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
