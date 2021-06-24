package com.alkemy.ong.services.Interface;

import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.UsersDto;
import com.alkemy.ong.model.UsersEntity;


@Service
public interface UsersService {
	
	public UsersEntity save(UsersEntity user);
	
	public UsersDto createUser(UsersDto user);
	
	public UsersDto getUser(String email);
	
	public UsersDto updateUser(Long id, UsersDto user);
	
	public void deleteUser(Long id);
	
	
	

	
}
