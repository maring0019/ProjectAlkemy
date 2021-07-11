package com.alkemy.ong.service.Interface;


import com.alkemy.ong.dto.TestimonialsDto;
import com.alkemy.ong.model.Testimonials;

public interface ITestimonials {

    public Testimonials findById(Long id);

    public Testimonials save(Testimonials testimonials);

    public TestimonialsDto create(TestimonialsDto testimonialsDto);

}
