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
	
	@Override
	public List<PostDTO> findAll() {
		final List<Post> posts = postRepository.findAll(Sort.by("id"));
		
		return posts.stream()
				.map(post -> mapToDTO(post, new PostDTO()))
				.toList();
	}

	@Override
	public PostDTO findById(final UUID id) {
		return postRepository.findById(id)
				.map(post -> mapToDTO(post, new PostDTO()))
				.orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
	}

	@Override
	public UUID create(final PostDTO postDTO) {
		Post post = new Post();
		mapToEntity(postDTO, post);
		return postRepository.save(post).getId();
	}

	@Override
	public void update(final PostDTO postDTO) {
		final Post post = postRepository.findById(postDTO.getId())
				.orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postDTO.getId()));
		mapToEntity(postDTO, post);
		postRepository.save(post);
		
	}

	@Override
	public void delete(final UUID id) {
		postRepository.deleteById(id);
		
	}

	// MAPPER
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
