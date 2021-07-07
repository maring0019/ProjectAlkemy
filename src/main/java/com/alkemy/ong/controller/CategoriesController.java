package com.alkemy.ong.controller;

import java.util.Locale;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import com.alkemy.ong.repository.CategoriesRepository;
import com.amazonaws.services.xray.model.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.CategoriesDto;
import com.alkemy.ong.service.Interface.ICategoriesService;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping(path = "/categories")
public class CategoriesController {
	
	@Autowired
	private MessageSource message;
	@Autowired
	private ICategoriesService iCategory;


	@PostMapping
	public ResponseEntity<?> post(@RequestBody CategoriesDto dto) throws EntityNotFoundException{
		try{
			return new ResponseEntity<>(iCategory.createCategory(dto) ,HttpStatus.OK);
		}catch (EntityNotFoundException e){
			return new ResponseEntity<>(message.getMessage("categories.error.object.notString", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CategoriesDto dto) throws EntityNotFoundException{
		try {
			return new ResponseEntity<>(iCategory.updateCategoryById(id, dto), HttpStatus.OK);
		}catch(EntityNotFoundException e) {
			return new ResponseEntity<>(message.getMessage("categories.error.object.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path="/{id}")
	public ResponseEntity<?> shearch(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(iCategory.findCategoriesById(id), HttpStatus.OK);
		}catch(EntityNotFoundException ex) {
				return new ResponseEntity<>(message.getMessage("categories.error.object.notFound", null, Locale.getDefault()), HttpStatus.BAD_REQUEST);
			}

		}

	@GetMapping()
	public ResponseEntity<?> getCategories(){
		return new ResponseEntity<>(iCategory.findAllWithName(), HttpStatus.OK);
	}

        
        @DeleteMapping(path = "/{id}")
        public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        try {
            if (iCategory.findCategoriesById(id)!= null)
                iCategory.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("Categoria eliminada satisfactoriamente.");
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
