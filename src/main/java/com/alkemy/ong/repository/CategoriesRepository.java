package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    Boolean existByID(Long id);
}
