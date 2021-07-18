package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.ContactsDto;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.service.Interface.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {
/*
GET /comments - Devolverá todos los comentarios, ordenados por fecha de creación, solamente el campo body
 */
    private ICommentService iCommentService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> commentsOrderedByDate(){
        return ResponseEntity.status(HttpStatus.OK).body(iCommentService.commentsOrderedByDate());
    }



}
