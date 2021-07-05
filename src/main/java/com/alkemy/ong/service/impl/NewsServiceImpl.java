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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;


@Service
public class NewsServiceImpl implements INewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MessageSource messageSource;


    public News getNewById(Long id) {
        return newsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        messageSource.getMessage("new.error.not.found", null, Locale.getDefault())
                )
        );
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
    public void deleteNews(Long id) {
        News newsEntity = getNewById(id);
        newsRepository.delete(newsEntity);
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
