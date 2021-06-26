package com.alkemy.ong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
	
	Optional<User> findById(Long id);
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);

}
