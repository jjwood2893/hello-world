package com.techelevator.dao;

import java.security.Principal;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Comic;

public interface ComicDAO {

	void addComic(Principal principal, Long collectionId, Comic comic);
	
	Comic getComicFromId(Long comicId);
	
	Comic mapRowToComic(SqlRowSet rs);
	
}
