package com.alkemy.ong.service.interfaces;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.NewsEntity;

import java.util.List;
import java.util.Optional;

public interface INewsService {
    public Optional<NewsEntity>findById(Long id);
    public List<NewsEntity> findAll();
    public NewsEntity save(NewsDto newsDto);
    public void deleteById(Long id);
    public NewsEntity updateNews(Long id, NewsDto newsDto);

}
