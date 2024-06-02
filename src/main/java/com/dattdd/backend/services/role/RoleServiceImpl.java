package com.dattdd.backend.services.role;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dattdd.backend.dto.RoleDTO;
import com.dattdd.backend.models.Role;
import com.dattdd.backend.repositories.RoleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements IRoleService{
	private final RoleRepository roleRepository;
	
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<RoleDTO> findAll() {
		final List<Role> roles = roleRepository.findAll(Sort.by("id"));
		
		return roles.stream()
				.map(role -> mapToDTO(role, new RoleDTO()))
				.toList();
	}

	@Override
	public RoleDTO findById(final Integer id) {
		return roleRepository.findById(id)
				.map(role -> mapToDTO(role, new RoleDTO()))
				.orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
	}

	@Override
	public Integer create(final RoleDTO roleDTO) {
		Role role = new Role();
		mapToEntity(roleDTO, role);
		return roleRepository.save(role).getId();
	}

	@Override
	public void update(final RoleDTO roleDTO) {
		final Role role = roleRepository.findById(roleDTO.getId())
				.orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleDTO.getId()));
		mapToEntity(roleDTO, role);
		roleRepository.save(role);
		
	}

	@Override
	public void delete(final Integer id) {
		roleRepository.deleteById(id);
		
	}

	// MAPPER
	private RoleDTO mapToDTO(Role role, RoleDTO roleDTO) {
		roleDTO.setId(role.getId());
		roleDTO.setName(role.getName());
		roleDTO.setDescription(role.getDescription());
		return roleDTO;
	}
	
	private Role mapToEntity(RoleDTO roleDTO, Role role) {
		role.setId(roleDTO.getId());
		role.setName(roleDTO.getName());
		role.setDescription(roleDTO.getDescription());
		return role;
	}
}
