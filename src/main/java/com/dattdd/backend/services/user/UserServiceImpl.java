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

	/**
	 * Retrieves a list of all UserDTO objects from the userRepository.
	 *
	 * @return         	A list of UserDTO objects.
	 */
	@Override
	public List<UserDTO> findAll() {
		final List<User> roles = userRepository.findAll(Sort.by("id"));
		
		return roles.stream()
				.map(user -> mapToDTO(user, new UserDTO()))
				.toList();
	}

	/**
	 * Retrieves a UserDTO object by its ID from the userRepository.
	 *
	 * @param  id  the UUID of the UserDTO object to retrieve
	 * @return     the UserDTO object if found, or throws an EntityNotFoundException if not found
	 */
	@Override
	public UserDTO findById(UUID id) {
		return userRepository.findById(id)
				.map(user -> mapToDTO(user, new UserDTO()))
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
	}

	/**
	 * Creates a new user using the provided UserDTO object and returns its UUID.
	 *
	 * @param  userDTO	the UserDTO object containing the data for the new user
	 * @return         	the UUID of the created user
	 */
	@Override
	public UUID create(UserDTO userDTO) {
		User user = new User();
		mapToEntity(userDTO, user);
		return userRepository.save(user).getId();
	}

	/**
	 * Updates a user with the provided UserDTO object.
	 *
	 * @param  userDTO	the UserDTO object containing the updated user data
	 * @throws EntityNotFoundException if the user with the provided ID is not found
	 */
	@Override
	public void update(UserDTO userDTO) {
		final User user = userRepository.findById(userDTO.getId())
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userDTO.getId()));
		mapToEntity(userDTO, user);
		userRepository.save(user);
		
	}

	/**
	 * Deletes a user from the user repository based on the provided ID.
	 *
	 * @param  id  the ID of the user to delete
	 */
	@Override
	public void delete(UUID id) {
		userRepository.deleteById(id);
		
	}
	
	/**
	 * Maps a User object to a UserDTO object.
	 *
	 * @param  user    the User object to map
	 * @param  userDTO the UserDTO object to populate
	 * @return         the populated UserDTO object
	 */
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
	
	/**
	 * Maps a UserDTO object to a User entity object.
	 *
	 * @param  userDTO  the UserDTO object to map
	 * @param  user     the User entity object to populate
	 * @return          the populated User entity object
	 */
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
