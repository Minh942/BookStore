package com.store.rest.controller;

import com.store.entity.Favorite;
import com.store.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/favorite")
public class FavoriteRestController {
	@Autowired
    FavoriteService favoriteService;
	
	@PostMapping("/add/{id}")
	public Favorite create(@PathVariable("id") int id) {
		return favoriteService.create(id);
	}
	
	@GetMapping("/{id}")
	public Favorite getOneFavorite(@PathVariable("id") int id) {
		return favoriteService.getOneFavorite(id);
	}
}
