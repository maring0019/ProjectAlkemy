package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.NewsCreationDto;
import com.alkemy.ong.dto.NewsResponseDto;
import com.alkemy.ong.model.News;
import org.springframework.data.domain.Page;

import java.util.List;

public interface INews {


    News getNewById(Long id);

    List<News> findAll();

    NewsResponseDto save(NewsCreationDto newsCreationDto);

    void deleteNews(Long id);

    NewsResponseDto updateNews(Long id, NewsCreationDto newsCreationDto);

    Page<NewsResponseDto> getAllNewsPaginated(int page, int limit, String sortBy, String sortDir);

}
