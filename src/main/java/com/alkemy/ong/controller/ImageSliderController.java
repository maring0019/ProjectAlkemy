package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ImageSlideCreationDto;
import com.alkemy.ong.service.Interface.IImgSlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/slides")
public class ImageSliderController {

    private final IImgSlideService iImgSlideService;

    @Autowired
    public ImageSliderController(IImgSlideService iImgSlideService) {
        this.iImgSlideService = iImgSlideService;
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

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSlide(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(iImgSlideService.deleteImage(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
