package com.techelevator.controller;

import java.io.Console;
import java.security.Principal;
import java.util.List;

import com.techelevator.dao.ComicDAO;

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
public class ComicsController {

	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private UserDAO userDAO;
	private CollectionDAO collectionDAO;
	private ComicDAO comicDAO;
	

	public ComicsController (TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, 
			UserDAO userDAO, CollectionDAO collectionDAO, ComicDAO comicDAO) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.userDAO = userDAO;
		this.collectionDAO = collectionDAO;
		this.comicDAO = comicDAO;
	}

	

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/collections/{collectionId}/add", method = RequestMethod.POST)
	public void addComic(Principal principal, @PathVariable Long collectionId, @RequestBody Comic comic ) {
	comicDAO.addComic(principal, collectionId, comic);
				}
	
	
}