package com.restaurant.demo.serviceImpl;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.restaurant.demo.entity.CustomUserDetail;
import com.restaurant.demo.entity.Role;
import com.restaurant.demo.entity.User;
import com.restaurant.demo.exception.BusinessException;
import com.restaurant.demo.exception.UserNotFoundException;
import com.restaurant.demo.repo.RoleRepo;
import com.restaurant.demo.repo.UserRepo;
import com.restaurant.demo.service.UserService;
import com.restaurant.demo.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	Role role;

	@Autowired
	private CustomUserDetailService customUserDeatilService;

	@Autowired
	CustomUserDetail customUserDetail;

	@Autowired
	JwtUtil jwtUtil;

	@Override
	public User saveUser(User user, HttpServletRequest request) {

		String role1 = fetchLoggedInUserDetails(request);
		if (role1.substring(5).equals("ADMIN")) {
			role1 = "ROLE_MANAGER";
		} else if (role1.substring(5).equals("MANAGER")) {
			throw new BusinessException(400, "Access denied for MANAGER role");
		}
		role1 = role1.substring(5);
		role = findByRole(role1);
		user.setRole(role);
		return userRepo.save(user);
	}

	protected Role findByRole(String role1) {
		Role role2 = roleRepo.findByRole(role1);
		if (role2 == null) {
			Role role3 = new Role();
			role3.setRole(role1);
			return roleRepo.save(role3);
		}
		return role2;
	}

	protected String fetchLoggedInUserDetails(HttpServletRequest request) {
		UserDetails userDetails = null;
		String role1 = null;
		final String authHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
			try {
				userDetails = customUserDeatilService.loadUserByUsername(username);
				role1 = userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			role1 = "ROLE_USER";
		}
		return role1;
	}

	@Override
	public User updateUser(User user, HttpServletRequest request, long id) {
		String userRole = fetchLoggedInUserDetails(request).substring(5);
		User existUser = userRepo.findById(id).orElseThrow(()-> new UserNotFoundException(id));
		if (userRole.equals("USER") && (!(existUser.getUserName().equals(user.getUserName()))
				|| !(existUser.getRole().getRole().equals(user.getRole().getRole())))) {
			throw new BusinessException(500, "Action Denied");
		}
		existUser.setUserName(user.getUserName());
		existUser.setUserPassword(user.getUserPassword());
		role.setRoleId(user.getRole().getRoleId());
		role.setRole(user.getRole().getRole());
		existUser.setRole(role);
		return userRepo.save(existUser);
	}

	@Override
	public User getUserById(long id) {
		return userRepo.findById(id).orElseThrow();
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
}
