package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ImageSlideCreationDto;
import com.alkemy.ong.model.ImageSlide;
import com.alkemy.ong.service.Interface.IImgSlideService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("/slides")
public class ImageSliderController {

    private final IImgSlideService iImgSlideService;
    private final MessageSource messageSource;

    @Autowired
    public ImageSliderController(IImgSlideService iImgSlideService, MessageSource messageSource) {
        this.iImgSlideService = iImgSlideService;
        this.messageSource = messageSource;
    }


    @PostMapping
    public ResponseEntity<Object> createImageSlide(@ModelAttribute ImageSlideCreationDto imageSlideCreationDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(iImgSlideService.createSlide(imageSlideCreationDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSlidesByOrganization(@RequestParam(value = "organization_id") Long organizationId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iImgSlideService.getAllSlidesByOrganization(organizationId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> shearch(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(iImgSlideService.getImageSlideById(id), HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(messageSource.getMessage("slide.error.do.not.exists", null, Locale.getDefault()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSlide(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(iImgSlideService.deleteImage(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping("/all")
	public ResponseEntity<?> getSlides(){
		List<ImageSlide> slider = iImgSlideService.getAll();
		return ResponseEntity.ok(slider);

	}

}





