package com.alkemy.ong.controller;

import java.util.Locale;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.CategoriesDto;
import com.alkemy.ong.service.Interface.ICategoriesService;

@RestController
@RequestMapping(path = "/categories")
public class CategoriesController {
	
	@Autowired
	private MessageSource message;
	@Autowired
	private ICategoriesService iCategory;

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CategoriesDto dto) throws EntityNotFoundException{
		try {
			return new ResponseEntity<>(iCategory.updateCategoryById(id, dto), HttpStatus.OK);
		}catch(EntityNotFoundException e) {
			return new ResponseEntity<>(message.getMessage("categories.error.object.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
		}
	}
}
