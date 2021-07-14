package com.alkemy.ong.controller;

import com.alkemy.ong.dto.request.NewsCreationDto;
import com.alkemy.ong.dto.response.NewsResponseDto;
import com.alkemy.ong.service.Interface.INews;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.Locale;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final INews newsService;
    private final MessageSource messageSource;
    private final ProjectionFactory projectionFactory;
    private final EntityManager entityManager;

    @Autowired
    public NewsController(INews newsService, MessageSource messageSource, ProjectionFactory projectionFactory, EntityManager entityManager) {
        this.newsService = newsService;
        this.messageSource = messageSource;
        this.projectionFactory = projectionFactory;
        this.entityManager = entityManager;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?>getNews(@PathVariable("id") Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(projectionFactory.createProjection(NewsResponseDto.class, newsService.getNewById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?>createNews(@ModelAttribute(name = "newsCreationDto") @Valid NewsCreationDto newsCreationDto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(newsService.save(newsCreationDto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteNews(@PathVariable Long id){
        try {
            newsService.deleteNews(id);
            return ResponseEntity.status(HttpStatus.OK).body(messageSource.getMessage("new.delete.successful",
                    null, Locale.getDefault()));
        } catch (Exception e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNews(@PathVariable Long id, @Valid @ModelAttribute(name = "newsCreationDto") NewsCreationDto newsCreationDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(newsService.updateNews(id, newsCreationDto));
        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("news.error.object.notFound", null, Locale.getDefault()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getNewsPaginated(@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value="limit", defaultValue = "10") int limit, @RequestParam(value = "sortBy", defaultValue = "created") String sortBy, @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir) {
        try {
            Session session = entityManager.unwrap(Session.class);
            Filter filter = session.enableFilter("deletedNewsFilter");
            filter.setParameter("isDeleted", isDeleted);
            Page<NewsResponseDto> newsPaged = newsService.getAllNewsPaginated(page, limit, sortBy, sortDir);
            session.disableFilter("deletedNewsFilter");
            return ResponseEntity.status(HttpStatus.OK).body(newsPaged);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
