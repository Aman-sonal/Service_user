package com.restaurant.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.demo.model.Employee;
import com.restaurant.demo.service.UserService;

@RestController
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	UserService userService;

	@Autowired
	UserController userController;

	@GetMapping
	public List<Employee> fetchEmployee() {
		List<Employee> employee = userService.fetchEmployee();
		return employee;	
	}

	@GetMapping("get-employee/{id}")
	public Employee fetcEmployeeById(@PathVariable("id") long id) {
		Employee employee = userService.fetchEmployeeById(id);
		return employee;
	}

	@PostMapping("add-employee")
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<Employee>(userService.saveEmployee(employee), HttpStatus.CREATED);
	}

	@PutMapping("update-employee/{id}")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable("id") long id) {
		return new ResponseEntity<Employee>(userService.updateEmployee(employee, id), HttpStatus.ACCEPTED);
	}

//	@PostMapping
//	public ResponseEntity<T> addEditPizza(@RequestBody Employee employee)
//	{
//		
//	}

//	Manager can View/Confirm/Cancel booking orders placed by the User.
//	Can check payments done by User.
//	Can change the availability of pizza in stock.
//	Can change prices of pizza.
//	Can manage Delivery staff.
//	Can manage “My Profiles”.
}
