package com.dattdd.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dattdd.backend.dto.RoleDTO;
import com.dattdd.backend.services.role.IRoleService;

@Controller
@RequestMapping("api/v1/roles")
public class RoleController {
	private final IRoleService roleService;

	public RoleController(IRoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * Retrieves a list of all RoleDTO objects from the roleService.
	 *
	 * @return         	A ResponseEntity containing a list of RoleDTO objects.
	 *                 	The HTTP status code is set to 200 (OK) if the list is not empty.
	 *                 	Otherwise, a 204 (No Content) status code is returned.
	 */
	@GetMapping
	public ResponseEntity<List<RoleDTO>> findAll() {
		return ResponseEntity.ok(roleService.findAll());
	}
	
	/**
	 * Retrieves a RoleDTO object by its ID from the roleService.
	 *
	 * @param  id  the ID of the RoleDTO object to retrieve
	 * @return     a ResponseEntity containing the RoleDTO object if found,
	 *             or a 404 Not Found status code if not found
	 */
	@GetMapping("/{id}")
	public ResponseEntity<RoleDTO> findById(@PathVariable final Integer id) {
		return ResponseEntity.ok(roleService.findById(id));
	}
	
	/**
	 * Creates a new role using the provided RoleDTO object and returns its ID.
	 *
	 * @param  roleDTO	the RoleDTO object containing the data for the new role
	 * @return         	a ResponseEntity containing the ID of the created role
	 *                 	with a HTTP status code of CREATED (201)
	 */
	@PostMapping
	public ResponseEntity<Integer> create(@RequestBody RoleDTO roleDTO) {
		Integer id = roleService.create(roleDTO);
		return new ResponseEntity<Integer>(id, HttpStatus.CREATED);
	}
}
