package com.dattdd.backend.services.like;

import org.springframework.stereotype.Service;

import com.dattdd.backend.repositories.LikeRepository;

@Service
public class LikeServiceImpl {
	private final LikeRepository likeRepository;
	
	public LikeServiceImpl(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}
}
