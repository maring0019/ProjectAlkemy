package com.alkemy.ong.service.Interface;

import java.util.List;

import com.alkemy.ong.dto.CategoriesDto;

public interface ICategoriesService {

	public CategoriesDto findById(Long id);

	public List<CategoriesDto> findAll();

	public CategoriesDto createCategory(CategoriesDto category);

	public void deleteById(Long id);

	public CategoriesDto updateCategoryById(Long id, CategoriesDto dto);
}
