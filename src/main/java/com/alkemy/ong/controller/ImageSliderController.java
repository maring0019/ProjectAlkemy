package com.alkemy.ong.controller;


import com.alkemy.ong.dto.ImageSlideDto;
import com.alkemy.ong.exception.InvalidImageException;
import com.alkemy.ong.model.ImageSlide;
import com.alkemy.ong.service.Interface.IImgSlideService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
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

    @PutMapping(path = "{id}")
    public ResponseEntity<ImageSlide> updateImage(@PathVariable Long id, @Valid @RequestBody ImageSlideDto imageSlideDto) throws InvalidImageException {
        try {
            ImageSlide updateImg = iImgSlideService.updateImage(id, imageSlideDto);
            return ResponseEntity.ok(updateImg);
        } catch (InvalidImageException e){
            throw new InvalidImageException(e.getMessage());
        }
    }
}
