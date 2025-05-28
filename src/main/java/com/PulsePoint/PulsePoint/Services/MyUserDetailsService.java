package com.PulsePoint.PulsePoint.Services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.PulsePoint.PulsePoint.Models.Users;
import com.PulsePoint.PulsePoint.Dao.UserDao;
import com.PulsePoint.PulsePoint.Models.UserPrincipal;


@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	Users user=dao.findByUsername(username);
	
	if (user==null) {
		System.out.println("User 404");
		throw new UsernameNotFoundException("User 404");
	}
		 return new UserPrincipal(user);
	}

}