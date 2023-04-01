package com.restaurant.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.demo.entity.AuthenticationRequest;
import com.restaurant.demo.entity.AuthenticationResponse;
import com.restaurant.demo.entity.Role;
import com.restaurant.demo.entity.User;
import com.restaurant.demo.service.UserService;
import com.restaurant.demo.serviceImpl.CustomUserDetailService;
import com.restaurant.demo.util.JwtUtil;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@MockBean
	private AuthenticationManager authenticationManager;

	@MockBean
	private CustomUserDetailService customUserDeatilService;

	@MockBean
	private JwtUtil jwtUtilToken;

	@MockBean
	AuthenticationRequest authenticationRequest;

	@MockBean
	AuthenticationResponse authenticationResponse;

	@MockBean
	Authentication authentication;

	@MockBean
	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

	private User user;
	private Role role;

	@BeforeEach
	public void setup() {
		user = new User();
		user.setUserName("Abcd");
		user.setUserPassword("abc");
		role = new Role();
		role.setRoleId(1);
		role.setRole("Customer");
		user.setRole(role);

		authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setUserName("Pihu51");
		authenticationRequest.setPassword("abc");

	}

	@Test
	void testCreateAuthenticationToken() throws JsonProcessingException, Exception {
		String str = "";
		Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUserName(), authenticationRequest.getPassword()))).thenReturn(authentication);
		Mockito.when(jwtUtilToken
				.generateToken(customUserDeatilService.loadUserByUsername(authenticationRequest.getUserName())))
				.thenReturn(str);
		mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(new ObjectMapper().writeValueAsString(authenticationRequest))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.jwt.size()", is(str.length())));

	}

}
