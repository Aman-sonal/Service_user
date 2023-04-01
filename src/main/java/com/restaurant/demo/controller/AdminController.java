package com.restaurant.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.demo.model.Employee;
import com.restaurant.demo.model.User;
import com.restaurant.demo.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	ManagerController managerController;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("fetch-employee-by-store-id/{storeId}")
	public List<Employee> fetcEmployeeByStoreId(@PathVariable("storeId") long storeId) {
		List<Employee> employee = userService.fetchEmployeeByStoreId(storeId);
		return employee;
	}

	@PutMapping("update-customer/{id}")
	public ResponseEntity<?> updateCustomerByAdmin(@RequestBody User user, @PathVariable("id") long id) {
		return new ResponseEntity<User>(userService.updateUserByAdmin(user, id), HttpStatus.ACCEPTED);
	}

//	Admin can View/Confirm/Cancel orders.

}
