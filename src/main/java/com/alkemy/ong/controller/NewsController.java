package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.service.Interface.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNews(@PathVariable Long id, @Valid @RequestBody NewsDto newsDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(iNewsService.updateNews(id, newsDto));
        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("news.error.object.notFound", null, Locale.getDefault()), HttpStatus.BAD_REQUEST);
        }
    }
}
