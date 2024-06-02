package com.dattdd.backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dattdd.backend.dto.PostDTO;
import com.dattdd.backend.services.post.IPostService;

@Controller
@RequestMapping("api/v1/posts")
public class PostController {
	private final IPostService postService;

	public PostController(IPostService postService) {
		this.postService = postService;
	}

	/**
	 * Retrieves a list of all PostDTO objects from the postService.
	 *
	 * @return         	A ResponseEntity containing a list of PostDTO objects.
	 *                 	The HTTP status code is set to 200 (OK) if the list is not empty.
	 *                 	Otherwise, a 204 (No Content) status code is returned.
	 */
	@GetMapping
	public ResponseEntity<List<PostDTO>> findAll() {
		return ResponseEntity.ok(postService.findAll());
	}
	
	/**
	 * Retrieves a PostDTO object by its ID from the postService.
	 *
	 * @param  id  the UUID of the PostDTO object to retrieve
	 * @return     a ResponseEntity containing the PostDTO object if found,
	 *             or a 404 Not Found status code if not found
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> findById(@PathVariable final UUID id) {
		return ResponseEntity.ok(postService.findById(id));
	}
	
	/**
	 * Creates a new PostDTO object using the provided data and returns its UUID.
	 *
	 * @param  postDTO	the data for the new PostDTO object
	 * @return         	a ResponseEntity containing the UUID of the created PostDTO object
	 *                 	with a HTTP status code of CREATED (201)
	 */
	@PostMapping
	public ResponseEntity<UUID> create(@RequestBody PostDTO postDTO) {
		UUID id = postService.create(postDTO);
		return new ResponseEntity<UUID>(id, HttpStatus.CREATED);
	}
}
