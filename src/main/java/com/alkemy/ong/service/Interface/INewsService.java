package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.News;

import java.util.List;
import java.util.Optional;

public interface INewsService {
    public Optional<News>findById(Long id);
    public List<News> findAll();
    public News save(NewsDto newsDto);
    public void deleteById(Long id);
    public News updateNews(Long id, NewsDto newsDto);

}
