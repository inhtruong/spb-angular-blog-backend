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

	/**
	 * Retrieves a list of all UserDTO objects from the userService.
	 *
	 * @return         	A ResponseEntity containing a list of UserDTO objects.
	 *                 	The HTTP status code is set to 200 (OK) if the list is not empty.
	 *                 	Otherwise, a 204 (No Content) status code is returned.
	 */
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok(userService.findAll());
	}
	
	/**
	 * Retrieves a UserDTO object by its ID from the userService.
	 *
	 * @param  id  the UUID of the UserDTO object to retrieve
	 * @return     a ResponseEntity containing the UserDTO object if found,
	 *             or a 404 Not Found status code if not found
	 */
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable final UUID id) {
		return ResponseEntity.ok(userService.findById(id));
	}
	
	/**
	 * Creates a new user using the provided UserDTO object and returns its UUID.
	 *
	 * @param  userDTO	the UserDTO object containing the data for the new user
	 * @return         	a ResponseEntity containing the UUID of the created user
	 *                 	with a HTTP status code of CREATED (201)
	 */
	@PostMapping
	public ResponseEntity<UUID> create(@RequestBody UserDTO userDTO) {
		UUID id = userService.create(userDTO);
		return new ResponseEntity<UUID>(id, HttpStatus.CREATED);
	}
	
	/**
	 * Updates a user with the specified ID using the provided UserDTO object.
	 *
	 * @param  id        the UUID of the user to update
	 * @param  userDTO   the UserDTO object containing the updated user data
	 * @return           a ResponseEntity containing the UUID of the updated user
	 *                   with a HTTP status code of OK (200)
	 */
	@PatchMapping("/{id}")
	public ResponseEntity<UUID> update(@PathVariable final UUID id, @RequestBody UserDTO userDTO) {
		userService.update(userDTO);
		return ResponseEntity.ok(userDTO.getId());
	}
}
