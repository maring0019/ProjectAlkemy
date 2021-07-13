package com.alkemy.ong.controller;
import com.alkemy.ong.dto.TestimonialsDto;
import com.alkemy.ong.model.Testimonials;
import com.alkemy.ong.service.Interface.IMemberService;
import com.alkemy.ong.service.Interface.ITestimonials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping
public class TestimonialsController {

    @Autowired
    private ITestimonials iTestimonials;

    @Autowired
    private MessageSource messageSource;


    @PutMapping("/testimonials/{id}")
    public ResponseEntity<?> Update(@RequestBody Testimonials testimonials, @PathVariable Long id){
        try {
            Testimonials testimonialsUpdate = iTestimonials.findById(id);

            testimonialsUpdate.setName(testimonials.getName());
            testimonialsUpdate.setImage(testimonials.getImage());
            testimonialsUpdate.setContent(testimonials.getContent());

            return ResponseEntity.status(HttpStatus.OK).body(iTestimonials.save(testimonialsUpdate));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @DeleteMapping(path = "/testimonials/{id}")
    public ResponseEntity<String> deleteTestimonialById(@PathVariable Long id) {
        try {
            if (iTestimonials.findById(id) != null)
                iTestimonials.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(messageSource.getMessage("testimonials.delete.successful",
                    null, Locale.getDefault()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/testimonials")
    public  ResponseEntity<?> AllPagination(@PageableDefault(size = 10 , page = 0 ) Pageable pageable) {

        try {
            Page<Testimonials> result = iTestimonials.showAllTestimonials(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    }


