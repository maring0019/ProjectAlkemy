package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsServiceImpl newsService;

    @GetMapping("/{id}")
    public ResponseEntity<?>getNews(@PathVariable("id") Long id){
        NewsDto newsDto = newsService.findById(id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(newsDto);
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
