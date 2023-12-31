package com.smartcontact.conf;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartcontact.entities.User;

public class CustomUserDetails implements UserDetails {

	private String email;
	private String password;
	private List<GrantedAuthority> roles;

	public CustomUserDetails(User user) {
		this.email = user.getEmail();
		this.password = user.getPassword();
		String arr[] = user.getRole().split(",");
		this.roles = Arrays.stream(arr).map(e -> new SimpleGrantedAuthority(e)).collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return email;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

}
