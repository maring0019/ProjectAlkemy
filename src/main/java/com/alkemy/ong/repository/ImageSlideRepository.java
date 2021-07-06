package com.alkemy.ong.repository;

import com.alkemy.ong.dto.ImageSlideDto;
import com.alkemy.ong.model.ImageSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageSlideRepository extends JpaRepository<ImageSlide, Long> {

    List<ImageSlide> findAllByOrganizationIdOrderByOrdered(Long organizationId);

    Optional<ImageSlide> findById(Long id);

}
