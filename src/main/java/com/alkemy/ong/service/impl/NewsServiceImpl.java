package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.Interface.INewsService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Service
public class NewsServiceImpl implements INewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelMapper mapper;


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
    public News save(NewsDto newsDto) {
        return null;
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
}
