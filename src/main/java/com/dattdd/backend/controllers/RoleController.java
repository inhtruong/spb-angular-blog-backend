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

	@GetMapping
	public ResponseEntity<List<RoleDTO>> findAll() {
		return ResponseEntity.ok(roleService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoleDTO> findById(@PathVariable final Integer id) {
		return ResponseEntity.ok(roleService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Integer> create(@RequestBody RoleDTO roleDTO) {
		Integer id = roleService.create(roleDTO);
		return new ResponseEntity<Integer>(id, HttpStatus.CREATED);
	}
}
