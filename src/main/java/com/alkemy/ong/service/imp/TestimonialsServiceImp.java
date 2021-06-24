package com.alkemy.ong.service.imp;

import com.alkemy.ong.model.TestimonialsEntity;
import com.alkemy.ong.repository.TestimonialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TestimonialsServiceImp implements com.alkemy.ong.service.Interface.ITestimonialsService {

    @Autowired
    TestimonialsRepository testimonialsRepo;

    @Override
    public void Save(TestimonialsEntity testimonial) {
             testimonialsRepo.save(testimonial);
    }

    @Override
    public void Edit(TestimonialsEntity testimonial, Long id) {
            testimonial.setId(id);
            testimonialsRepo.save(testimonial);
    }

    @Override
    public List<TestimonialsEntity> searchAll() {
        return testimonialsRepo.findAll();
    }

    @Override
    public TestimonialsEntity searchById(Long id) {
        Optional<TestimonialsEntity> optional = testimonialsRepo.findById(id);
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
