package com.alkemy.ong.repository;

import com.alkemy.ong.model.ActivitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivitiesRepository extends JpaRepository<ActivitiesEntity, Long> {

    Optional<ActivitiesEntity> findById(Long id);

}
