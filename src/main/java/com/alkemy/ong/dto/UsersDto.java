package com.alkemy.ong.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsersDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "FirstName is required.")
	private String firstName;
	
	@NotBlank(message = "LastName is required.")
	private String lastName;
	
	@NotBlank(message = "Email is required.")
	@Email(message = "Invalid Email.")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;
	
	private String photo;
	
	private Set<Rol> roles = new HashSet<>();
	

}
