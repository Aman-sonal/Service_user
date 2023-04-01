package com.restaurant.demo.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.restaurant.demo.repo.RoleRepo;

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
		// TODO Auto-generated constructor stub
		this.user=user;
	}
	
	
	
	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		// TODO Auto-generated method stub
//		long roleId= user.getRole().getRoleId();
//		role=roleRepo.findById(roleId).orElseThrow();
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
