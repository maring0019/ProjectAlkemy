package com.alkemy.ong.controller;

import com.alkemy.ong.service.Interface.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private MessageSource messageSource;

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
