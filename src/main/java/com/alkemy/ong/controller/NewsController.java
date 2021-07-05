package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.News;
import com.alkemy.ong.service.impl.NewsServiceImpl;
import com.alkemy.ong.service.Interface.INewsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private INewsService iNewsService;

    @Autowired
    private NewsServiceImpl newsService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<?>getNews(@PathVariable("id") Long id){
        NewsDto newsDto = mapper.map(newsService.getNewById(id), NewsDto.class);
        if(newsDto.getId() != null){
            return ResponseEntity.status(HttpStatus.OK).body(newsDto);
        }else{
            return new ResponseEntity<>(messageSource.getMessage("news.error.object.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<?>createNews(@Valid @RequestBody NewsDto newsDto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(newsService.save(newsDto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable Long id){
        try {
            iNewsService.deleteNews(id);
            return ResponseEntity.status(HttpStatus.OK).body(messageSource.getMessage("new.delete.successful",
                    null, Locale.getDefault()));
        } catch (Exception e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
