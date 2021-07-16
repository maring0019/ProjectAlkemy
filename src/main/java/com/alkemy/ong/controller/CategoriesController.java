package com.alkemy.ong.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.alkemy.ong.dto.response.CategoryResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.request.CategoryCreationDto;
import com.alkemy.ong.service.Interface.ICategoriesService;
import org.springframework.web.bind.annotation.DeleteMapping;
@Api(value = "Categorias controller")
@RestController
@RequestMapping("/categories")
public class CategoriesController {

	private final ICategoriesService iCategory;

	@Autowired
	public CategoriesController(ICategoriesService iCategory) {
		this.iCategory = iCategory;
	}

	@ApiOperation("Crear categoria")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Operación exitosa"),
			@ApiResponse(code = 400, message = "Solicitud incorrecta")
	})
	@PostMapping
	public ResponseEntity<?> post(@Valid @ModelAttribute(name = "categoryCreationDto") CategoryCreationDto categoryCreationDto) throws EntityNotFoundException{
		try{
			return new ResponseEntity<>(iCategory.createCategory(categoryCreationDto) ,HttpStatus.CREATED);
		}catch (EntityNotFoundException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation("Actualizar categoria")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Operación exitosa"),
			@ApiResponse(code = 400, message = "Solicitud incorrecta")
	})
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @ModelAttribute(name = "categoryCreationDto") CategoryCreationDto categoryCreationDto) {
		try {
			return new ResponseEntity<>(iCategory.updateCategoryById(id, categoryCreationDto), HttpStatus.OK);
		}catch(EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation("Buscar por id a categoria")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Operación exitosa"),
			@ApiResponse(code = 400, message = "Solicitud incorrecta")
	})
	@GetMapping(path="/{id}")
	public ResponseEntity<?> shearch(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(iCategory.findById(id), HttpStatus.OK);
		} catch(EntityNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation("Buscar todas las categorias")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Operación exitosa"),
			@ApiResponse(code = 400, message = "Solicitud incorrecta")
	})
	@GetMapping
	public ResponseEntity<List<CategoryResponseDto>> getCategories(){
		return new ResponseEntity<>(iCategory.findAllWithName(), HttpStatus.OK);
	}

	@ApiOperation("Eliminar por id a categoria")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Operación exitosa"),
			@ApiResponse(code = 400, message = "Solicitud incorrecta")
	})
	@DeleteMapping(path = "/{id}")
        public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iCategory.deleteById(id));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
