package com.alkemy.ong.service.Interface;


import com.alkemy.ong.dto.TestimonialsDto;
import com.alkemy.ong.model.Testimonials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITestimonials {

    public Testimonials findById(Long id);

    public Testimonials save(Testimonials testimonials);


    public void deleteById(Long id);


    public TestimonialsDto create(TestimonialsDto testimonialsDto);

    public Page<Testimonials> showAllTestimonials(Pageable pageable);
}
