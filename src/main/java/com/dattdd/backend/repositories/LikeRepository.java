package com.dattdd.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dattdd.backend.models.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{

}
