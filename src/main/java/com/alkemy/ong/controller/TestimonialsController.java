package com.alkemy.ong.controller;
import com.alkemy.ong.dto.TestimonialsDto;
import com.alkemy.ong.model.Testimonials;
import com.alkemy.ong.service.Interface.IMemberService;
import com.alkemy.ong.service.Interface.ITestimonials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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


    /* Borrado de testimonials según el id pasado como parámetro*/
    @DeleteMapping(path = "/testimonials/{id}")
    public ResponseEntity<String> deleteTestimonialById(@PathVariable Long id) {
        try {
            if (iTestimonials.findById(id) != null)
                iTestimonials.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Testimonial eliminado satisfactoriamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
