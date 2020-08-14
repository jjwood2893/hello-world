package com.techelevator.controller;

import java.io.Console;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.el.stream.Optional;
import com.techelevator.dao.CollectionDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Collection;
import com.techelevator.model.Comic;
import com.techelevator.model.LoginDTO;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;
import com.techelevator.security.jwt.JWTFilter;
import com.techelevator.security.jwt.TokenProvider;

@RestController
@CrossOrigin
public class CollectionsController {

	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private UserDAO userDAO;
	private CollectionDAO collectionDAO;

	public CollectionsController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
			UserDAO userDAO, CollectionDAO collectionDAO) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.userDAO = userDAO;
		this.collectionDAO = collectionDAO;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/collections", method = RequestMethod.POST)
	public void createCollection(Principal principal, @RequestBody Collection collection ) {
	collectionDAO.createCollection(principal, collection.getName(), collection.isPublicCollection());
				}

	@RequestMapping(value = "/collections/{collectionId}/comics", method = RequestMethod.GET)
	public List<Comic> viewCollection(Principal principal, @PathVariable Long collectionId) {
		if (principal == null) {
			return collectionDAO.viewCollection(collectionId);
		}
		return collectionDAO.viewCollection(principal, collectionId);
	}
	
	@RequestMapping(value = "/collections/{collectionId}", method = RequestMethod.GET)
	public Collection getCollection(Principal principal, @PathVariable Long collectionId) {
		if (principal == null) {
			return collectionDAO.getCollection(collectionId);
		}
		return collectionDAO.getCollection(collectionId);
	}

	@RequestMapping(value = "/collections", method = RequestMethod.GET)
	public List<Collection> viewCollections(Principal principal) {
		if (principal == null) {
			return collectionDAO.viewCollections();
		} else {
			return collectionDAO.viewCollections(principal);
		}
	}

	@RequestMapping(value = "/collections/user/{username}", method = RequestMethod.GET)
	public List<Collection> viewMyCollections(Principal principal, @PathVariable String username) {
		return collectionDAO.viewMyCollections(principal, username);
	}
	
//	@RequestMapping(value = "/collections/{collection_id}/character/{maincharacter}", method = RequestMethod.GET)
//	public int getNumberOfIssuesForCharacterInCollection(Principal principal, @PathVariable int collection_id, @PathVariable String maincharacter) {
//		return collectionDAO.getNumberOfIssuesForCharacterInCollection(principal, collection_id, maincharacter);
//	}
	
	@RequestMapping(value = "/collections/{collection_id}/author/{author}", method = RequestMethod.GET)
	public int getNumberOfIssuesForAuthorInCollection(Principal principal, @PathVariable int collection_id, @PathVariable String author) {
		return collectionDAO.getNumberOfIssuesForAuthorInCollection(principal, collection_id, author);
	}
	
	@RequestMapping(value = "/collections/{collection_id}/artist/{artist}", method = RequestMethod.GET)
	public int getNumberOfIssuesForArtistInCollection(Principal principal, @PathVariable int collection_id, @PathVariable String artist) {
		return collectionDAO.getNumberOfIssuesForArtistInCollection(principal, collection_id, artist);
	}
	
//	@RequestMapping(value = "/collections/character/{maincharacter}", method = RequestMethod.GET)
//	public int getNumberOfIssuesForCharacterInCollections(Principal principal, @PathVariable String maincharacter) {
//		return collectionDAO.getNumberOfIssuesForCharacterInCollections(principal, maincharacter);
//	}
	
	@RequestMapping(value = "/collections/author/{author}", method = RequestMethod.GET)
	public int getNumberOfIssuesForAuthorInCollections(Principal principal, @PathVariable String author) {
		return collectionDAO.getNumberOfIssuesForAuthorInCollections(principal, author);
	}
	
	@RequestMapping(value = "/collections/artist/{artist}", method = RequestMethod.GET)
	public int getNumberOfIssuesForArtistInCollections(Principal principal, @PathVariable String artist) {
		return collectionDAO.getNumberOfIssuesForArtistInCollections(principal, artist);
	}
	
	@RequestMapping(value = "/collections/character/popular", method = RequestMethod.GET)
	public String getMostPopularCharacterInCollections() {
		return collectionDAO.getMostPopularCharacterInCollections();
	}
	
	@RequestMapping(value = "/collections/author/popular", method = RequestMethod.GET)
	public String getMostPopularAuthorInCollections() {
		return collectionDAO.getMostPopularAuthorInCollections();
	}
	
	@RequestMapping(value = "/collections/artist/popular", method = RequestMethod.GET)
	public String getMostPopularArtistInCollections() {
		return collectionDAO.getMostPopularArtistInCollections();
	}
	
	@RequestMapping(value = "/collections/{collection_id}/character/popular", method = RequestMethod.GET)
	public String getMostPopularCharacterInCollection(@PathVariable int collection_id) {
		return collectionDAO.getMostPopularCharacterInCollection(collection_id);
	}
	
	@RequestMapping(value = "/collections/{collection_id}/author/popular", method = RequestMethod.GET)
	public String getMostPopularAuthorInCollection(@PathVariable int collection_id) {
		return collectionDAO.getMostPopularAuthorInCollection(collection_id);
	}
	
	@RequestMapping(value = "/collections/{collection_id}/artist/popular", method = RequestMethod.GET)
	public String getMostPopularArtistInCollection(@PathVariable int collection_id) {
		return collectionDAO.getMostPopularArtistInCollection(collection_id);
	}
	
	@RequestMapping(value = "/collections/{collection_id}/comic/{comic_id}", method = RequestMethod.DELETE)
	public void removeComicFromCollection(@PathVariable int collection_id, @PathVariable int comic_id) {
		collectionDAO.removeComicFromCollection(collection_id, comic_id);
	}


}
