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

	@GetMapping
	public ResponseEntity<List<PostDTO>> findAll() {
		return ResponseEntity.ok(postService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> findById(@PathVariable final UUID id) {
		return ResponseEntity.ok(postService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<UUID> create(@RequestBody PostDTO postDTO) {
		UUID id = postService.create(postDTO);
		return new ResponseEntity<UUID>(id, HttpStatus.CREATED);
	}
}
