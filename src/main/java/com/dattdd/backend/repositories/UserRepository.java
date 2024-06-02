package com.dattdd.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dattdd.backend.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

}
