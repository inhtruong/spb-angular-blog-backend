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

	/**
	 * Retrieves a list of all RoleDTO objects from the roleRepository.
	 *
	 * @return         	A list of RoleDTO objects.
	 */
	@Override
	public List<RoleDTO> findAll() {
		final List<Role> roles = roleRepository.findAll(Sort.by("id"));
		
		return roles.stream()
				.map(role -> mapToDTO(role, new RoleDTO()))
				.toList();
	}

	/**
	 * Finds a RoleDTO by its ID.
	 *
	 * @param  id  the ID of the RoleDTO to find
	 * @return     the RoleDTO with the given ID, or throws an EntityNotFoundException if not found
	 */
	@Override
	public RoleDTO findById(final Integer id) {
		return roleRepository.findById(id)
				.map(role -> mapToDTO(role, new RoleDTO()))
				.orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
	}

	/**
	 * Creates a new role based on the provided RoleDTO object and saves it to the role repository.
	 *
	 * @param  roleDTO  the RoleDTO object containing the details of the role to be created
	 * @return          the ID of the newly created role
	 */
	@Override
	public Integer create(final RoleDTO roleDTO) {
		Role role = new Role();
		mapToEntity(roleDTO, role);
		return roleRepository.save(role).getId();
	}

	/**
	 * Updates a role based on the provided RoleDTO object.
	 *
	 * @param  roleDTO  the RoleDTO object containing the updated details of the role
	 * @throws EntityNotFoundException if the role with the provided ID is not found
	 */
	@Override
	public void update(final RoleDTO roleDTO) {
		final Role role = roleRepository.findById(roleDTO.getId())
				.orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleDTO.getId()));
		mapToEntity(roleDTO, role);
		roleRepository.save(role);
		
	}

	/**
	 * Deletes a role from the role repository based on the provided ID.
	 *
	 * @param  id  the ID of the role to delete
	 */
	@Override
	public void delete(final Integer id) {
		roleRepository.deleteById(id);
		
	}

	/**
	 * Maps a Role object to a RoleDTO object by setting the ID, name, and description fields of the RoleDTO object.
	 *
	 * @param  role       the Role object to map to a RoleDTO object
	 * @param  roleDTO    the RoleDTO object to set the ID, name, and description fields of
	 * @return            the RoleDTO object with the ID, name, and description fields set
	 */
	private RoleDTO mapToDTO(Role role, RoleDTO roleDTO) {
		roleDTO.setId(role.getId());
		roleDTO.setName(role.getName());
		roleDTO.setDescription(role.getDescription());
		return roleDTO;
	}
	
	/**
	 * Maps the fields of a RoleDTO object to a Role entity.
	 *
	 * @param  roleDTO  the RoleDTO object containing the updated details of the role
	 * @param  role     the Role entity to be updated
	 * @return          the updated Role entity
	 */
	private Role mapToEntity(RoleDTO roleDTO, Role role) {
		role.setId(roleDTO.getId());
		role.setName(roleDTO.getName());
		role.setDescription(roleDTO.getDescription());
		return role;
	}
}
