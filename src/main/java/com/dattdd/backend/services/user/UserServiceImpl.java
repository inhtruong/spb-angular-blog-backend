package com.dattdd.backend.services.user;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dattdd.backend.dto.UserDTO;
import com.dattdd.backend.models.User;
import com.dattdd.backend.repositories.RoleRepository;
import com.dattdd.backend.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements IUserService{
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public List<UserDTO> findAll() {
		final List<User> roles = userRepository.findAll(Sort.by("id"));
		
		return roles.stream()
				.map(user -> mapToDTO(user, new UserDTO()))
				.toList();
	}

	@Override
	public UserDTO findById(UUID id) {
		return userRepository.findById(id)
				.map(user -> mapToDTO(user, new UserDTO()))
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
	}

	@Override
	public UUID create(UserDTO userDTO) {
		User user = new User();
		mapToEntity(userDTO, user);
		return userRepository.save(user).getId();
	}

	@Override
	public void update(UserDTO userDTO) {
		final User user = userRepository.findById(userDTO.getId())
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userDTO.getId()));
		mapToEntity(userDTO, user);
		userRepository.save(user);
		
	}

	@Override
	public void delete(UUID id) {
		userRepository.deleteById(id);
		
	}
	
	// MAPPER
		private UserDTO mapToDTO(User user, UserDTO userDTO) {
			userDTO.setId(user.getId());
			userDTO.setEmail(user.getEmail());
			userDTO.setPassword(user.getPassword());
			userDTO.setFullName(user.getFullName());
			userDTO.setActive(user.isActive());
			userDTO.setCreateDate(user.getCreateDate());
			userDTO.setCreatedBy(user.getCreatedBy());
			userDTO.setModifiedDate(user.getModifiedDate());
			userDTO.setModifiedBy(user.getModifiedBy());
			userDTO.setRoleId(user.getRole().getId());
			return userDTO;
		}
		
		private User mapToEntity(UserDTO userDTO, User user) {
			user.setId(userDTO.getId());
			user.setEmail(userDTO.getEmail());
			user.setPassword(userDTO.getPassword());
			user.setFullName(userDTO.getFullName());
			user.setActive(userDTO.isActive());
			user.setCreateDate(userDTO.getCreateDate());
			user.setCreatedBy(userDTO.getCreatedBy());
			user.setModifiedDate(userDTO.getModifiedDate());
			user.setModifiedBy(userDTO.getModifiedBy());
			user.setRole(roleRepository.findById(userDTO.getRoleId())
					.orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + userDTO.getRoleId())));
			return user;
		}

}
