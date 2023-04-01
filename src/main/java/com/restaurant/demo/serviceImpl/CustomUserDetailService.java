package com.restaurant.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restaurant.demo.entity.CustomUserDetail;
import com.restaurant.demo.entity.User;
import com.restaurant.demo.repo.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, NullPointerException {
		User user = userRepo.findByUserName(username);
		CustomUserDetail customUserDetail = new CustomUserDetail(user);
		customUserDetail.setUser(user);
		return customUserDetail;
	}

	public UserRepo getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepo userRepo){
		this.userRepo = userRepo;
	}
}
