package com.restaurant.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.restaurant.demo.model.Employee;
import com.restaurant.demo.model.Role;
import com.restaurant.demo.model.User;
import com.restaurant.demo.service.UserService;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private UserService userService;

	@MockBean
	private UserController userController;

	@MockBean
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private User user;
	private Employee emp;
	private Role role;
	
	@BeforeEach
	public void setup() {
		user= new User();
		user.setUserName("ABCD");
		user.setUserPassword("abc");
		role= new Role();
		role.setRole("Admin");
		role.setRoleId(3);
		user.setRole(role);
		
		
		emp= new Employee();
		emp.setEmpId(1);
		emp.setEmpName("emp1");
		emp.setEmpRole("xxx");
	}
//	@Test
//	void testSaveAdmin() {
//			
//	}	

//	@Test
//	void testAddStoreManager() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFetcEmployeeByStoreId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateCustomerByAdmin() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateEmployeeByAdmin() {
//		fail("Not yet implemented");
//	}

}
