package com.restaurant.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.demo.model.Role;
import com.restaurant.demo.model.User;
import com.restaurant.demo.service.UserService;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	BCryptPasswordEncoder bCryptPasswordEncoder;
	private User user;
	private Role role;

	@BeforeEach
	public void setup() {
		user = new User();
		user.setUserName("Abcd");
		user.setUserPassword(bCryptPasswordEncoder.encode("abc"));
		role = new Role();
		role.setRoleId(1);
		role.setRole("Customer");
		user.setRole(role);
	}

	@Test
	public void testSaveUser() throws Exception {
		Mockito.when(userService.saveUser(user, "Customer")).thenReturn(user);

		mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(new ObjectMapper().writeValueAsString(user)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.userId", is(user.getUserId())))
				.andExpect(jsonPath("$.userName", is(user.getUserName())));
	}

	@Test
	public void testUpdateCustomer() throws Exception {
		Mockito.when(userService.saveUser(user, "Customer")).thenReturn(user);
		user.setUserName("John");
		Mockito.when(userService.updateUser(user, user.getUserId())).thenReturn(user);
		mockMvc.perform(put("/customer/update-customer/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(new ObjectMapper().writeValueAsString(user))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.userId", is(user.getUserId())))
				.andExpect(jsonPath("$.userName", is(user.getUserName())));
	}
}
