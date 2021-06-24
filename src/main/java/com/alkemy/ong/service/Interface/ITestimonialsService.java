package com.alkemy.ong.service.Interface;

import com.alkemy.ong.model.TestimonialsEntity;

import java.util.List;

public interface ITestimonialsService {

    void Save(TestimonialsEntity testimonial);
    void Edit(TestimonialsEntity testimonial, Long id);
    List<TestimonialsEntity> searchAll();
    TestimonialsEntity searchById(Long id);
    void remove(Long id);

}
