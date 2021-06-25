package com.alkemy.ong.service.Impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.News;
import com.alkemy.ong.service.interfaces.INewsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements INewsService {
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
    public void deleteById(Long id) {

    }

    @Override
    public News updateNews(Long id, NewsDto newsDto) {
        return null;
    }
}
