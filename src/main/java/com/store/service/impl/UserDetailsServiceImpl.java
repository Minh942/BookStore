package com.store.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.service.UserService;

import org.springframework.security.core.userdetails.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	// Thong tin user service;
	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder pe;
	
	/**
	 * Cung cap quyen cho project
	 * 
	 * @param username
	 * @return userDetails
	 * @exception UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.store.entity.User appUser = this.userService.findUserByEmail(username);
		if(appUser == null) {
			System.out.println("User not found! "+username);
			throw new UsernameNotFoundException("User " + username + " was not found in database");		
		}
		else {
			System.out.println("User found! "+username);
			System.out.println("Password: " + appUser.getPassword());
		}
		
		List<GrantedAuthority> grandList = new ArrayList<GrantedAuthority>();
		
		String password = pe.encode(appUser.getPassword());
		UserDetails userDetails = (UserDetails) new User(appUser.getEmail(), password, grandList);
		
		return userDetails;
	}

}
