package com.alkemy.ong.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginUsersDto {

	@NotBlank(message = "Email is required.")
	private String email;
	
	@NotBlank(message = "Password is required.")
	private String password;
}
