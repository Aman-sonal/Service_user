package com.restaurant.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.demo.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{
	Role findByRole(String role);
}
