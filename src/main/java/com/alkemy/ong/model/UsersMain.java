package com.alkemy.ong.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsersMain implements UserDetails{

	private String firstName;
	private String lastName;
	private String email;
	private String photo;
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public UsersMain(String firstName, String lastName, String email, String photo, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.photo = photo;
		this.password = password;
		this.authorities = authorities;
	}

	public static UsersMain build(User user) {
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getName().name()))
				.collect(Collectors.toList());
		
		return new UsersMain(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoto(), user.getPassword(), authorities);
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	
	
	

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
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
