package com.alkemy.ong.controller;


import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.ImageSlideDto;
import com.alkemy.ong.service.Interface.IImgSlideService;


@RestController
@RequestMapping("/slides")
@AllArgsConstructor
public class ImageSliderController {
	
	private final IImgSlideService slideService;
	
	@GetMapping
	public ResponseEntity<?> getSlides(){
		List<ImageSlideDto> sliderDto = slideService.getAll();
		return ResponseEntity.ok(sliderDto);
	}
	
}
