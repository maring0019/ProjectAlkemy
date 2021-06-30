package com.alkemy.ong.controller;

import com.alkemy.ong.service.Interface.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NewsController {

    @Autowired
    private INewsService iNewsService;

    @DeleteMapping("/DELETE/news/{id}")
    public void deleteNews(@PathVariable Long id){
        iNewsService.deleteNews(id);

    }

}
