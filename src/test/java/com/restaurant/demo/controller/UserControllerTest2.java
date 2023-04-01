package com.restaurant.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.demo.entity.AuthenticationRequest;
import com.restaurant.demo.entity.AuthenticationResponse;
import com.restaurant.demo.entity.Role;
import com.restaurant.demo.entity.User;
import com.restaurant.demo.service.UserService;
import com.restaurant.demo.serviceImpl.CustomUserDetailService;
import com.restaurant.demo.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest2 {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@MockBean
	AuthenticationManager authenticationManager;
	
	@MockBean
	CustomUserDetailService customUserDetailService;
	
	@MockBean
	JwtUtil jwtUtil;
	
	@MockBean
	AuthenticationRequest authenticationRequest;
	
	@MockBean
	AuthenticationResponse authenticationResponse;
	
	private User user;
	private Role role;

	@BeforeEach
	void setUp() throws Exception {
		user = new User();
		user.setUserName("Abc@cd");
		user.setUserPassword(bCryptPasswordEncoder.encode("abc"));
		role = new Role();
		role.setRoleId(1);
		role.setRole("USER");
		user.setRole(role);
	}

	@Test
	void testCreateCustomer() throws JsonProcessingException, Exception {
		HttpServletRequest request = null;
		Mockito.when(userService.saveUser(user, request)).thenReturn(user);
		mockMvc.perform(post("/user").with(new RequestPostProcessor() {

			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {

				request.setParameter("Authorization",
						"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbWFuU29uIiwiZXhwIjoxNjc3MjcwMzg0LCJpYXQiOjE2NzcyMzQzODR9.mjxzkw8ZThnEGGiDpd9DizHaMufLNb7B13oP8TYldIc");
				return request;
			}
		}).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(new ObjectMapper().writeValueAsString(user)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.userId", is(user.getUserId())))
				.andExpect(jsonPath("$.userName", is(user.getUserName())));
	}

	@Test
	void testGetUserById() throws JsonProcessingException, Exception {
		HttpServletRequest request = null;
		Mockito.when(userService.saveUser(user, request)).thenReturn(user);
		Mockito.when(userService.getUserById(user.getUserId())).thenReturn(user);
		
		mockMvc.perform(get("/user/{id}",user.getUserId()).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(new ObjectMapper().writeValueAsString(user)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.userId", is(user.getUserId()>0)));
	}

	@Test
	void testGetAllUser() throws JsonProcessingException, Exception {
		HttpServletRequest request = null;
		List<User> userList= new ArrayList<>();
		Mockito.when(userService.saveUser(user, request)).thenReturn(user);
		userList.add(user);
		User user2= new User();
		user2 = new User();
		user2.setUserName("xy@za");
		user2.setUserPassword(bCryptPasswordEncoder.encode("abc"));
		role = new Role();
		role.setRoleId(2);
		role.setRole("MANAGER");
		user2.setRole(role);
		Mockito.when(userService.getUserById(user2.getUserId())).thenReturn(user2);
		userList.add(user2);
		
		Mockito.when(userService.getAllUsers()).thenReturn(userList);
		
		
		mockMvc.perform(get("/user/all").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(new ObjectMapper().writeValueAsString(user)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.userList.size()", is(userList.size()>0)));
		
	}

	@Test
	void testUpdateCustomer() throws JsonProcessingException, Exception {
		HttpServletRequest request = null;
		Mockito.when(userService.saveUser(user, request)).thenReturn(user);
		user.setUserName("a@bcd");
		Mockito.when(userService.updateUser(user, request,user.getUserId())).thenReturn(user);
		mockMvc.perform(put("/user/update/{id}",user.getUserId()).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(new ObjectMapper().writeValueAsString(user)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.user", is(user.getUserId())));
	}

}
