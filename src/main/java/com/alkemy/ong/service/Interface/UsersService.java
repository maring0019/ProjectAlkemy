package com.alkemy.ong.service.Interface;

import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.UsersDto;
import com.alkemy.ong.model.User;


@Service
public interface UsersService {
	
	public User save(User user);
	
	public UsersDto createUser(UsersDto user);
	
	public UsersDto getUser(String email);
	
	public UsersDto updateUser(Long id, UsersDto user);
	
	public void deleteUser(Long id);
	
	
	

	
}
