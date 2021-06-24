package com.alkemy.ong.repository;

import com.alkemy.ong.model.ActivitiesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivitiesRepository extends CrudRepository<ActivitiesEntity, Long> {

    Optional<ActivitiesEntity> findById(Long id);

    List<ActivitiesEntity> findAll();

}
