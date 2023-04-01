package com.restaurant.demo.serviceImpl;

import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.restaurant.demo.entity.Role;
import com.restaurant.demo.entity.User;
import com.restaurant.demo.repo.RoleRepo;
import com.restaurant.demo.repo.UserRepo;
import com.restaurant.demo.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepo userRepo;

	@MockBean
	private RoleRepo roleRepo;
	
	
	private User user;
	private Role role;

	@BeforeEach
	public void setup() {

		user = new User();
		user.setUserName("abc");
		user.setUserPassword("abc");
		role = new Role();
		role.setRole("Customer");
		role.setRoleId(1);
		user.setRole(role);
	}

	@DisplayName("Junit test for saving user")
	@Test
	void testSaveUser() {
		Mockito.when(userRepo.save(user)).thenReturn(user);
		assertThat(userService.saveUser(user, new MockHttpServletRequest())).isEqualTo(user);
	}

	@Test
	void testUpdateUser() {
		user.setUserName("xyz");
		user.setUserPassword("abcd");
		Mockito.when(userRepo.save(user)).thenReturn(user);
		assertThat(user.getUserName()).isEqualTo("xyz");
		assertThat(user.getUserPassword()).isEqualTo("abcd");
	}
	@Test
	void testGetUserById() {
		Mockito.when(userRepo.findById(user.getUserId())).thenReturn(Optional.ofNullable(user));
		assertThat(userService.getUserById(user.getUserId())).isEqualTo(user);
	}
	@Test
	void testGetAllUser()
	{	
		List<User> listUser= new ArrayList<>();
		User user1 = new User();
		user1.setUserName("abc");
		user1.setUserPassword("abc");
		Role role1 = new Role();
		role1.setRole("Customer");
		role1.setRoleId(1);
		user1.setRole(role);
		listUser.add(user);
		listUser.add(user1);
		Mockito.when(userRepo.findAll()).thenReturn(listUser);
		assertThat(userService.getAllUsers().size()).isGreaterThan(0);
	}
}
