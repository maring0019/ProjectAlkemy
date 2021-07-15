package com.alkemy.ong.service.impl;
import com.alkemy.ong.dto.TestimonialsDto;
import com.alkemy.ong.model.Testimonials;
import com.alkemy.ong.repository.TestimonialsRepository;
import com.alkemy.ong.service.Interface.ITestimonials;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TestimonialsServiceImpl implements ITestimonials {

    @Autowired
    private TestimonialsRepository testimonialsRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Testimonials findById(Long id) {
        return testimonialsRepository.findById(id).get();
    }

    @Override
    public Testimonials save(Testimonials testimonials) {
        return testimonialsRepository.save(testimonials);
    }



    @Override
    public void deleteById(Long id) {testimonialsRepository.deleteById(id); }



    @Override
    public TestimonialsDto create(TestimonialsDto testimonialsDto) {

        Testimonials testimonials = Testimonials.builder()
                .name(testimonialsDto.getName())
                .content(testimonialsDto.getContent())
                .image(testimonialsDto.getImage())
                .created(new Date())
                .deleted(false)
                .build();
        return mapper.map(testimonialsRepository.save(testimonials),TestimonialsDto.class);
    }

    @Override
    public Page<Testimonials> showAllTestimonials(Pageable pageable) {
        return testimonialsRepository.findAll(pageable);
    }

}
