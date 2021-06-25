package com.alkemy.ong.service.Interface;


import com.alkemy.ong.model.Testimonials;

import java.util.List;

public interface ITestimonials {

    void Save(Testimonials testimonial);

    void Edit(Testimonials testimonial, Long id);

    List<Testimonials> searchAll();

    Testimonials searchById(Long id);

    void remove(Long id);

}
