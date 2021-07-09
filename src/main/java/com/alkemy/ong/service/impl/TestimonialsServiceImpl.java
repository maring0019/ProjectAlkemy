package com.alkemy.ong.service.impl;
import com.alkemy.ong.model.Testimonials;
import com.alkemy.ong.repository.TestimonialsRepository;
import com.alkemy.ong.service.Interface.ITestimonials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestimonialsServiceImpl implements ITestimonials {

    @Autowired
    private TestimonialsRepository testimonialsRepository;

    @Override
    public Testimonials findById(Long id) {
        return testimonialsRepository.findById(id).get();
    }

    @Override
    public Testimonials save(Testimonials testimonials) {
        return testimonialsRepository.save(testimonials);
    }
}
