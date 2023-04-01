package com.restaurant.demo.entity;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationResponse {

	private final String jwt;

	public String getJwt() {
		return jwt;
	}

	public AuthenticationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public AuthenticationResponse() {
		this.jwt = "";
		// TODO Auto-generated constructor stub
	}
	

}
