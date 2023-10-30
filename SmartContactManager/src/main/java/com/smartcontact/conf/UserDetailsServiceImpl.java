package com.smartcontact.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartcontact.entities.User;
import com.smartcontact.repo.SmartUserRepo;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private SmartUserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException(username+" not found");
		}
		
		return new CustomUserDetails(user);
	}
	

}
