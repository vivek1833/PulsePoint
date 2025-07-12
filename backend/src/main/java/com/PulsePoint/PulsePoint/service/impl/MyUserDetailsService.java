package com.PulsePoint.PulsePoint.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.PulsePoint.PulsePoint.model.Users;
import com.PulsePoint.PulsePoint.dto.UserPrincipal;
import com.PulsePoint.PulsePoint.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
	private final UserRepo repo;

	public MyUserDetailsService(UserRepo repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repo.findByUsername(username);
		if (user == null) {
			System.out.println("User 404");
			throw new UsernameNotFoundException("User 404");
		}
		if (Boolean.FALSE.equals(user.getActive())) {
			System.out.println("User not active");
			throw new UsernameNotFoundException("User not active");
		}
		return new UserPrincipal(user);
	}
}