package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.Categories;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.Interface.INewsService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements INewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public NewsDto findById(Long id) {
        return mapper.map(newsRepository.findById(id), NewsDto.class);
    }

    @Override
    public List<News> findAll() {
        return null;
    }

    @Override
    public NewsDto save(NewsDto newsDto) {
        News newsEntity = News.builder()
                .name(newsDto.getName())
                .content(newsDto.getContent())
                .image(newsDto.getImage())
                .category(mapper.map(newsDto.getCategory(), Categories.class))
                .build();
        return mapper.map(newsRepository.save(newsEntity), NewsDto.class);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public NewsDto updateNews(Long id, NewsDto newsDto) {
        News newsEntity = newsRepository.getById(id);
        if(newsDto.getName() != null ){newsEntity.setName(newsDto.getName());}
        if(newsDto.getContent() != null){newsEntity.setContent(newsDto.getContent());}
        if(newsDto.getImage() != null){newsEntity.setImage(newsDto.getImage());}
        if(newsDto.getCategory() != null){newsEntity.setCategory(mapper.map(newsDto.getCategory(), Categories.class));}
        newsEntity.setEdited(new Date());
        return mapper.map(newsRepository.save(newsEntity), NewsDto.class);
    }
}
