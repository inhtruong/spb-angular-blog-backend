package com.dattdd.backend.services.post;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dattdd.backend.dto.PostDTO;
import com.dattdd.backend.models.Post;
import com.dattdd.backend.repositories.PostRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostServiceImpl implements IPostService {
	private final PostRepository postRepository;

	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	/**
	 * Retrieves a list of all PostDTO objects from the postRepository.
	 *
	 * @return         	A list of PostDTO objects.
	 */
	@Override
	public List<PostDTO> findAll() {
		final List<Post> posts = postRepository.findAll(Sort.by("id"));
		
		return posts.stream()
				.map(post -> mapToDTO(post, new PostDTO()))
				.toList();
	}

	/**
	 * Retrieves a PostDTO object by its ID from the postRepository.
	 *
	 * @param  id  the UUID of the PostDTO object to retrieve
	 * @return     the PostDTO object with the given ID, or throws an EntityNotFoundException if not found
	 */
	@Override
	public PostDTO findById(final UUID id) {
		return postRepository.findById(id)
				.map(post -> mapToDTO(post, new PostDTO()))
				.orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
	}

	/**
	 * Creates a new PostDTO object by mapping the provided PostDTO to a Post entity,
	 * saving the Post entity to the postRepository, and returning the UUID of the saved Post.
	 *
	 * @param  postDTO  the PostDTO object to be mapped and saved
	 * @return          the UUID of the saved Post
	 */
	@Override
	public UUID create(final PostDTO postDTO) {
		Post post = new Post();
		mapToEntity(postDTO, post);
		return postRepository.save(post).getId();
	}

	/**
	 * Updates a PostDTO object by finding the corresponding Post entity in the postRepository,
	 * mapping the provided PostDTO to the Post entity, saving the updated Post entity to the
	 * postRepository, and throwing an EntityNotFoundException if the Post with the provided
	 * id is not found.
	 *
	 * @param  postDTO  the PostDTO object to be updated
	 * @throws EntityNotFoundException  if the Post with the provided id is not found
	 */
	@Override
	public void update(final PostDTO postDTO) {
		final Post post = postRepository.findById(postDTO.getId())
				.orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postDTO.getId()));
		mapToEntity(postDTO, post);
		postRepository.save(post);
		
	}

	/**
	 * Deletes a post with the specified ID from the post repository.
	 *
	 * @param  id  the ID of the post to be deleted
	 */
	@Override
	public void delete(final UUID id) {
		postRepository.deleteById(id);
		
	}

	/**
	 * Maps a Post entity to a PostDTO object.
	 *
	 * @param  post     the Post entity to be mapped
	 * @param  postDTO  the PostDTO object to be populated
	 * @return          the populated PostDTO object
	 */
	private PostDTO mapToDTO(Post post, PostDTO postDTO) {
		postDTO.setId(post.getId());
		postDTO.setTitle(post.getTitle());
		postDTO.setContent(post.getContent());
		postDTO.setTags(post.getTags());
		postDTO.setCreateDate(post.getCreateDate());
		postDTO.setCreatedBy(post.getCreatedBy());
		postDTO.setModifiedDate(post.getModifiedDate());
		postDTO.setModifiedBy(post.getModifiedBy());
		
		return postDTO;
	}
	
	/**
	 * Maps the fields of a PostDTO object to a Post entity.
	 *
	 * @param  postDTO  the PostDTO object to be mapped
	 * @param  post     the Post entity to be updated
	 * @return          the updated Post entity
	 */
	private Post mapToEntity(PostDTO postDTO, Post post) {
		post.setId(postDTO.getId());
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setTags(postDTO.getTags());
		post.setCreateDate(postDTO.getCreateDate());
		post.setCreatedBy(postDTO.getCreatedBy());
		post.setModifiedDate(postDTO.getModifiedDate());
		post.setModifiedBy(postDTO.getModifiedBy());
		return post;
	}

}
