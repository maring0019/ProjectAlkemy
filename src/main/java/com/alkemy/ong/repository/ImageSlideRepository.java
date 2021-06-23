package com.alkemy.ong.repository;

import com.alkemy.ong.model.ImageSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageSlideRepository extends JpaRepository<ImageSlide, Long> {
}
