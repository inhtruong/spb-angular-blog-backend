package com.dattdd.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dattdd.backend.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
