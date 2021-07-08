package com.alkemy.ong.repository;

import com.alkemy.ong.model.ImageSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> e2fcd982cda01f5deb5e6285dbf64aa239492315
import java.util.Optional;

@Repository
public interface ImageSlideRepository extends JpaRepository<ImageSlide, Long> {
<<<<<<< HEAD

    List<ImageSlide> findAllByOrganizationIdOrderByOrdered(Long organizationId);

    Optional<ImageSlide> findById(Long id);

=======
    Optional<ImageSlide> findById(Long id);
>>>>>>> e2fcd982cda01f5deb5e6285dbf64aa239492315
}
