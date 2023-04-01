package com.restaurant.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Payload {
	
	private User user;
	private AuthenticationResponse authenticationResponse;
	public Payload() {
		// TODO Auto-generated constructor stub
	}
	public Payload(User user, AuthenticationResponse authenticationResponse) {
		super();
		this.user = user;
		this.authenticationResponse = authenticationResponse;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public AuthenticationResponse getAuthenticationResponse() {
		return authenticationResponse;
	}
	public void setAuthenticationResponse(AuthenticationResponse authenticationResponse) {
		this.authenticationResponse = authenticationResponse;
	}

}
