package com.alkemy.ong.service.impl;
import com.alkemy.ong.model.Testimonials;
import com.alkemy.ong.repository.TestimonialsRepository;
import com.alkemy.ong.service.Interface.ITestimonials;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


public class TestimonialsServiceImpl implements ITestimonials {

    @Autowired

    private TestimonialsRepository testimonialsRepo;

    @Override

    public void Save(Testimonials testimonial) {

        testimonialsRepo.save(testimonial);

    }


    @Override

    public void Edit(Testimonials testimonial, Long id) {

        testimonial.setId(id);

        testimonialsRepo.save(testimonial);

    }


    @Override

    public List<Testimonials> searchAll() {

        return testimonialsRepo.findAll();

    }


    @Override

    public Testimonials searchById(Long id) {

        Optional<Testimonials> optional = testimonialsRepo.findById(id);

        if(optional.isPresent()){

            return optional.get();

        }

        return null;

    }


    @Override

    public void remove(Long id) {

        testimonialsRepo.deleteById(id);

    }
}
