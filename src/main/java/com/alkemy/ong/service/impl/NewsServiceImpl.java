package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.Interface.INewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements INewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public Optional<News> findById(Long id) {
        return Optional.empty();
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
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public News updateNews(Long id, NewsDto newsDto) {
        return null;
    }
}
