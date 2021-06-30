package com.alkemy.ong.service.impl;

import javax.json.JsonPatch;
import javax.persistence.EntityNotFoundException;

import com.alkemy.ong.util.PatchHelper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alkemy.ong.dto.UsersDto;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UsersRepository;
import com.alkemy.ong.service.Interface.IUsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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


	@Override
	public UsersDto createUser(UsersDto user) {

		if(usersRepository.findByEmail(user.getEmail()).isPresent())
			throw new RuntimeException(messageSource.getMessage("user.error.email.registered", null, Locale.getDefault()));

		User userEntity = User.builder()
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.password(passwordEncoder.encode(user.getPassword()))
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
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = usersRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));
		return User.build(user);
	}

	@Override
	public List<UsersDto> showAllUsers() {
		return mapper.map(usersRepository.findAll(), (Type) UsersDto.class);
	}


}
