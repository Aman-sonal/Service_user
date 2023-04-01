package com.restaurant.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.demo.entity.User;
import com.restaurant.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	//Add user , admin, manager according to role
	@PostMapping
	public ResponseEntity<?> createCustomer(@Valid @RequestBody User user, HttpServletRequest request) {
		String encodePwd = bCryptPasswordEncoder.encode(user.getUserPassword());
		user.setUserPassword(encodePwd);
		return new ResponseEntity<User>(userService.saveUser(user, request), HttpStatus.CREATED);
	}
	
	// get USER by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable long id) {
		return userService.getUserById(id);
	}

	// get user by MANAGER,ADMIN
	@GetMapping("/all")
	public List<User> getAllUser() {
		return userService.getAllUsers();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user,BindingResult bindingResult, HttpServletRequest request,@PathVariable long id) {
		String encodePwd = bCryptPasswordEncoder.encode(user.getUserPassword());
		user.setUserPassword(encodePwd);
		return new ResponseEntity<User>(userService.updateUser(user, request, id), HttpStatus.ACCEPTED);
	}
}
