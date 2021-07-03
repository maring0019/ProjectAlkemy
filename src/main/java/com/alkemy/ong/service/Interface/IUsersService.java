package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.LoginUsersDto;
import com.alkemy.ong.dto.UsersDto;
import com.alkemy.ong.exception.NotRegisteredException;
import com.alkemy.ong.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.json.JsonPatch;
import java.util.List;


public interface IUsersService extends UserDetailsService {

	UsersDto createUser(UsersDto user);

	UsersDto getUser(String email);

	UsersDto updateUser(Long id, UsersDto user);

	void deleteUser(Long id);

	User getUserById(Long id);

	UsersDto patchUpdate(Long id, JsonPatch patchDocument);

	UserDetails loadUserByUsername(String email);

	String loginUser(LoginUsersDto user) throws NotRegisteredException;

	List<UsersDto> showAllUsers();


}
