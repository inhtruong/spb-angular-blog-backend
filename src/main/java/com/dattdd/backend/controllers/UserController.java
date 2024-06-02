package com.dattdd.backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dattdd.backend.dto.UserDTO;
import com.dattdd.backend.services.user.IUserService;

@Controller
@RequestMapping("api/v1/users")
public class UserController {
	private final IUserService userService;

	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok(userService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable final UUID id) {
		return ResponseEntity.ok(userService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<UUID> create(@RequestBody UserDTO userDTO) {
		UUID id = userService.create(userDTO);
		return new ResponseEntity<UUID>(id, HttpStatus.CREATED);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<UUID> update(@PathVariable final UUID id, @RequestBody UserDTO userDTO) {
		userService.update(userDTO);
		return ResponseEntity.ok(userDTO.getId());
	}
}
