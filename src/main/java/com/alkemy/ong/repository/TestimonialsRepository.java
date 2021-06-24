package com.alkemy.ong.repository;
import com.alkemy.ong.model.TestimonialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialsRepository extends JpaRepository<TestimonialsEntity,Long> {

}
