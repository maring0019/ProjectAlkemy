package com.alkemy.ong.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.alkemy.ong.dto.CategoriesNameDto;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.CategoriesDto;
import com.alkemy.ong.model.Categories;
import com.alkemy.ong.repository.CategoriesRepository;
import com.alkemy.ong.service.Interface.ICategoriesService;

@Service
public class CategoriesServiceImpl implements ICategoriesService {

      private CategoriesRepository ctgRepo;
      private MessageSource messageSource;
      private ModelMapper mapper;

      @Autowired
      public CategoriesServiceImpl(CategoriesRepository repo, MessageSource msg, ModelMapper model) {
          this.ctgRepo = repo;
          this.mapper = model;
          this.messageSource = msg;
      }

      @Override
      public CategoriesDto createCategory(CategoriesDto dto) throws EntityNotFoundException {
          Categories category = new Categories(
                  dto.getName(),
                  dto.getDescription()
          );
          return mapper.map(ctgRepo.save(category), CategoriesDto.class);

      }

      @Override
      public CategoriesDto updateCategoryById(Long id, CategoriesDto dto) {

          Categories updateCategory = findCategoriesById(id);

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

      @Override
      public Categories findCategoriesById(Long id) {
          return ctgRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                  messageSource.getMessage("categories.error.object.notFound", null, Locale.getDefault())));
      }

    @Override
    public CategoriesNameDto findAllWithName() {
        return mapper.map(ctgRepo.findAll(), CategoriesNameDto.class);

    }

    public static boolean isNumeric(String nombre){
        boolean resultado;
        try{
            Long.parseLong(nombre);
            resultado = true;
        }catch(NumberFormatException e){
            resultado = false;
        }
        return resultado;
    }
}