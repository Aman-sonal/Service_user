package com.restaurant.demo.service;

import java.util.List;
import com.restaurant.demo.entity.Role;
import com.restaurant.demo.entity.User;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	User saveUser(User user, HttpServletRequest request);

	User updateUser(User user, HttpServletRequest request, long id);

	User getUserById(long id);

	List<User> getAllUsers();
}
