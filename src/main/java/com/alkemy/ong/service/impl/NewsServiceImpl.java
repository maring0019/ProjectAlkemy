package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsResponseDto;
import com.alkemy.ong.model.Categories;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.Interface.INews;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;


import org.springframework.context.MessageSource;
import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Locale;


@Service
public class NewsImpl implements INews {

    private final NewsRepository newsRepository;
    private final  ModelMapper mapper;
    private final MessageSource messageSource;

    @Autowired
    public NewsImpl(NewsRepository newsRepository, ModelMapper mapper, MessageSource messageSource) {
        this.newsRepository = newsRepository;
        this.mapper = mapper;
        this.messageSource = messageSource;
    }


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

        News upNews = getNewById(id);

        if((newsDto.getCategory() == null)) {
            newsDto.setCategory(upNews.getCategory());
        }
        if(!(newsDto.getCategory().getId() == null)) {
            upNews.setCategory(newsDto.getCategory());
        }
        if(!newsDto.getName().isBlank()){
            upNews.setName(newsDto.getName());
        }
        if(!newsDto.getContent().isBlank()){
            upNews.setContent(newsDto.getContent());
        }
        if(!newsDto.getImage().isBlank()) {
            upNews.setImage(newsDto.getImage());
        }
        upNews.setEdited(new Date());
        return mapper.map(newsRepository.save(upNews), NewsDto.class);

    }

    @Override
    public Page<NewsResponseDto> getAllNewsPaginated(int page, int limit, String sortBy, String sortDir) {
        if (page > 0) {
            page = page - 1;
        }

        Pageable pageable = PageRequest.of(
                page, limit,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        return newsRepository.findAllProjectedBy(pageable);
    }
}
