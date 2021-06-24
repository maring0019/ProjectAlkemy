package com.alkemy.ong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.UsersEntity;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
	
	Optional<UsersEntity> findById(Long id);
	
	Optional<UsersEntity> findByEmail(String email);
	
	boolean existsByEmail(String email);

}
