package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.Interface.INewsService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public News save(NewsDto newsDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public News updateNews(Long id, NewsDto newsDto) {
        return null;
    }
}
