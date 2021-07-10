package com.alkemy.ong.service.Interface;


import com.alkemy.ong.model.Testimonials;

public interface ITestimonials {

    public Testimonials findById(Long id);

    public Testimonials save(Testimonials testimonials);

    public void deleteById(Long id);

}
