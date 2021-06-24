package com.alkemy.ong.controller;

import com.alkemy.ong.service.Interface.ITestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestimonialController {

    @Autowired
    ITestimonialsService testimonialsSer;


}
