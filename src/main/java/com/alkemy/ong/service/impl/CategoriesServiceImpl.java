package com.alkemy.ong.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.CategoriesDto;
import com.alkemy.ong.model.Categories;
import com.alkemy.ong.repository.CategoriesRepository;
import com.alkemy.ong.service.Interface.ICategoriesService;

@Service
public class CategoriesServiceImpl implements ICategoriesService {

	@Autowired
	private CategoriesRepository ctgRepo;

	private ModelMapper mapper = modelMapper();

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

	@Override
	public CategoriesDto createCategory(CategoriesDto dto) {

		Categories category = new Categories();
		category.setName(dto.getName());
		category.setDescription(dto.getDescription());
		category.setImage(dto.getImage());

		return mapper.map(ctgRepo.save(category), CategoriesDto.class);
	}

	@Override
	public CategoriesDto updateCategoryById(Long id, CategoriesDto dto) {

		Categories updateCategory = ctgRepo.findById(id).get();
		if (!dto.getName().isBlank()) {
			updateCategory.setName(dto.getName());
		} else {
		}
		if (!dto.getImage().isBlank()) {
			updateCategory.setImage(dto.getImage());
		} else {
		}
		if (!dto.getDescription().isBlank()) {
			updateCategory.setDescription(dto.getDescription());
		} else {
		}

		updateCategory.setEdited(new Date());

		return mapper.map(ctgRepo.save(updateCategory), CategoriesDto.class);
	}

	@Override
	public CategoriesDto findById(Long id) {

		return mapper.map(ctgRepo.findById(id), CategoriesDto.class);
	}

	@Override
	public List<CategoriesDto> findAll() {

		List<Categories> categories = new ArrayList<>();
		categories = ctgRepo.findAll();

		List<CategoriesDto> dto = new ArrayList<>();
		categories.forEach(c -> dto.add(mapper.map(c, CategoriesDto.class)));

		return dto;
	}

	@Override
	public void deleteById(Long id) {
		ctgRepo.deleteById(id);
	}
}
