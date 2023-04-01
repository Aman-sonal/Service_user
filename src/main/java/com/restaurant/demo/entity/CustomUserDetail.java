package com.restaurant.demo.entity;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.restaurant.demo.repo.RoleRepo;

@Configuration
public class CustomUserDetail implements UserDetails{

	/**
	 * 
	 */
	@Autowired
	User user;
	
	@Autowired
	Role role;
	
	@Autowired
	RoleRepo roleRepo;
	
	private static final long serialVersionUID = 1L;
	
	
	public CustomUserDetail(User user) {
		super();
		this.user=user;
	}
	
	
	
	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() throws NullPointerException{
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getUserPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
