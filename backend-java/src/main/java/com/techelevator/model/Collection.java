package com.techelevator.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Collection {
	   private Long collectionId;
	   private String name;
	   private Long userId; 
	   private boolean publicCollection;
	
	 
	   
	   public Collection() {
		// TODO Auto-generated constructor stub
	}

	   public Collection(Long collectionId, String name, Long userId, boolean isPublic) {
	      this.setCollectionId(collectionId);
	      this.setName(name);
	      this.setUserId(userId);
	      this.setPublicCollection(isPublic);
	   
	   }

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isPublicCollection() {
		return publicCollection;
	}

	public void setPublicCollection(boolean publicCollection) {
		this.publicCollection = publicCollection;
	}



}
