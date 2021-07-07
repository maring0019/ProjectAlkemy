package com.alkemy.ong.service.impl;

import javax.json.JsonPatch;
import javax.persistence.EntityNotFoundException;


import com.alkemy.ong.Enum.ERole;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RolRepository;

import com.alkemy.ong.dto.LoginUsersDto;
import com.alkemy.ong.exception.NotRegisteredException;
import com.alkemy.ong.util.PatchHelper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alkemy.ong.dto.UsersDto;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UsersRepository;
import com.alkemy.ong.security.JwtProvider;
import com.alkemy.ong.service.Interface.IUsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import java.util.HashSet;

import java.util.List;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements IUsersService {

	@Autowired
	private final UsersRepository usersRepository;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private final ModelMapper mapper;

	@Autowired
	private final MessageSource messageSource;

	@Autowired
	private final PatchHelper patchHelper;

	@Autowired
	private final RolRepository rolRepository;

	@Autowired
	private final JwtProvider jwtProvider;

	@Autowired
	private final EmailServiceImpl emailService;



	@Override
	public UsersDto createUser(UsersDto user) throws IOException {

		if(usersRepository.findByEmail(user.getEmail()).isPresent())
			throw new RuntimeException(messageSource.getMessage("user.error.email.registered", null, Locale.getDefault()));

		Set<Role> roles = new HashSet<>();
		roles.add(rolRepository.findByRoleName(ERole.ROLE_USER).get());
		user.setRoles(roles);

		User userEntity = User.builder()
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.password(passwordEncoder.encode(user.getPassword()))
				.photo(user.getPhoto())
				.roles(user.getRoles())
				.build();

		emailService.registerEmail(user.getEmail());

		return mapper.map(usersRepository.save(userEntity), UsersDto.class);
	}

	@Override
	public String loginUser(LoginUsersDto user) throws NotRegisteredException {
		boolean isRegistered = loadUserByUsername(user.getEmail()).getUsername().equals(user.getEmail());
		if(isRegistered)
			return jwtProvider.generatedToken((User) loadUserByUsername(user.getEmail()));
		else
			throw new NotRegisteredException(messageSource.getMessage("login.error.email.not.registered", null, Locale.getDefault()));
	}

	@Override
	public UsersDto getUser(String email) {
		Optional<User> usr = usersRepository.findByEmail(email);
		User user = usr.get();
		
		UsersDto info = new UsersDto();
		info.setEmail(email);
		info.setFirstName(user.getFirstName());
		info.setLastName(user.getLastName());
		info.setPhoto(user.getPhoto());
		info.setCreated(user.getCreated());
		info.setEdited(user.getEdited());
		info.setRoles(user.getRoles());
		
		return info;
	}

	@Override
	public UsersDto updateUser(Long id, UsersDto user) {
		User userEntity = getUserById(id);
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		return mapper.map(usersRepository.save(userEntity), UsersDto.class);
	}

	@Override
	public void deleteUser(Long id) {
		User userEntity = getUserById(id);
		usersRepository.delete(userEntity);
	}

	@Override
	public User getUserById(Long id) {
		return usersRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(messageSource.getMessage("user.error.not.found", null, Locale.getDefault()))
		);
	}

	//https://cassiomolin.com/2019/06/10/using-http-patch-in-spring/
	@Override
	public UsersDto patchUpdate(Long id, JsonPatch patchDocument) {
		User user = getUserById(id);
		User userPatched = patchHelper.patch(patchDocument, user, User.class);
		userPatched.setEdited(new Date());
		return mapper.map(usersRepository.save(userPatched), UsersDto.class);
	}

	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = usersRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));
		return User.build(user);
	}

	@Override
	public List<UsersDto> showAllUsers() {
		return mapper.map(usersRepository.findAll(), (Type) UsersDto.class);
	}


}