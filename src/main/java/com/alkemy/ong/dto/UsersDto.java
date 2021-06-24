package com.alkemy.ong.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class UsersDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private String photo;
	
	private Set<Rol> roles = new HashSet<>();
	

}
