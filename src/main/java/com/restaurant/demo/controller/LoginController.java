package com.restaurant.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.demo.entity.AuthenticationRequest;
import com.restaurant.demo.entity.AuthenticationResponse;
import com.restaurant.demo.exception.BusinessException;
import com.restaurant.demo.serviceImpl.CustomUserDetailService;
import com.restaurant.demo.util.JwtUtil;

@RestController
@RequestMapping("/authenticate")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailService customUserDeatilService;

	@Autowired
	private JwtUtil jwtUtilToken;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	AuthenticationRequest authenticationRequest;

	@Autowired
	AuthenticationResponse response;

	public LoginController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationReq)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationReq.getUserName(),
					authenticationReq.getPassword()));
		} catch (BadCredentialsException bde) {
			throw new BusinessException(401, bde.getMessage());
		}
		final UserDetails userDetail = customUserDeatilService.loadUserByUsername(authenticationReq.getUserName());
		final String jwt = jwtUtilToken.generateToken(userDetail);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));

	}
}
