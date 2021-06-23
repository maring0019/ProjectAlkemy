package com.alkemy.ong.repository;

import com.alkemy.ong.model.testimonialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface testimonialsRepository extends JpaRepository<testimonialsEntity, Long> {
}
