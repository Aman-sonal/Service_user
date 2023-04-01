package com.restaurant.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.demo.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	User findByUserName(String username);
}
