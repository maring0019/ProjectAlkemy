package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.News;

import java.util.List;

public interface INewsService {


    public News getNewById(Long id);

    public List<News> findAll();

    public NewsDto save(NewsDto newsDto);

    public void deleteNews(Long id);

    public NewsDto updateNews(Long id, NewsDto newsDto);

}
