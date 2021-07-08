package com.alkemy.ong.controller;


import com.alkemy.ong.dto.ImageSlideDto;
import com.alkemy.ong.service.Interface.IImgSlideService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping(path="/slides")
@AllArgsConstructor
public class ImageSliderController {
    @Autowired
    private MessageSource message;
    @Autowired
    private IImgSlideService iImgSlideService;

    @GetMapping(path="/{id}")
    public ResponseEntity<?> shearch(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(iImgSlideService.getImageSlideById(id), HttpStatus.OK);
        }catch(EntityNotFoundException ex) {
            return new ResponseEntity<>(message.getMessage("slide.error.do.not.exists", null, Locale.getDefault()), HttpStatus.BAD_REQUEST);
        }

    }
    
    @GetMapping
	public ResponseEntity<?> getSlides(){
		List<ImageSlideDto> sliderDto = iImgSlideService.getAll();
		return ResponseEntity.ok(sliderDto);

	}

}
