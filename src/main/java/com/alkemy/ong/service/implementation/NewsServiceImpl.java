package com.alkemy.ong.service.implementation;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.NewsEntity;
import com.alkemy.ong.service.interfaces.INewsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements INewsService {
    @Override
    public Optional<NewsEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<NewsEntity> findAll() {
        return null;
    }

    @Override
    public NewsEntity save(NewsDto newsDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public NewsEntity updateNews(Long id, NewsDto newsDto) {
        return null;
    }
}
