package com.alkemy.ong.service.impl;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alkemy.ong.dto.UsersDto;
import com.alkemy.ong.model.User;
import com.alkemy.ong.model.UsersMain;
import com.alkemy.ong.repository.UsersRepository;
import com.alkemy.ong.service.Interface.UsersService;

@Transactional
public class UsersServiceImpl implements UsersService, UserDetailsService{
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ModelMapper mapper;
	

	@Override
	public User save(com.alkemy.ong.model.User user) {
		
		return null;
	}

	@Override
	public UsersDto createUser(UsersDto user) {
		
		if(usersRepository.findByEmail(user.getEmail()).isPresent()) throw new RuntimeException("Email is already registered.");
		
		User userEntity = User.builder()
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.password(bCryptPasswordEncoder.encode(user.getPassword()))
				.photo(user.getPhoto())
				.build();
		
		return mapper.map(usersRepository.save(userEntity), UsersDto.class);
	}

	@Override
	public UsersDto getUser(String email) {
		return mapper.map(usersRepository.findByEmail(email), UsersDto.class);
	}

	@Override
	public UsersDto updateUser(Long id, UsersDto user) {
		User userEntity = usersRepository.getById(id);
		userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return mapper.map(usersRepository.save(userEntity), UsersDto.class);
	}

	@Override
	public void deleteUser(Long id) {
		User userEntity = usersRepository.getById(id);
		usersRepository.delete(userEntity);		
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = usersRepository.findByEmail(email).get();
		return UsersMain.build(user);
	}

}
