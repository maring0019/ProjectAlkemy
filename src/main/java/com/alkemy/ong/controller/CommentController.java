package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.ContactsDto;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.service.Interface.ICommentService;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.request.CommentCreationDto;
import com.alkemy.ong.security.JwtFilter;
import com.alkemy.ong.security.JwtProvider;
import com.alkemy.ong.service.Interface.ICommentService;
import com.amazonaws.services.lexruntime.model.NotAcceptableException;


@RestController
@RequestMapping(path = "/comments")
public class CommentController {

	private final MessageSource message;
	private final JwtFilter jwtFilter;
	private final  JwtProvider jwtProvider;
	private final  ICommentService iComment;


	private ICommentService iCommentService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/comments")
	public ResponseEntity<List<CommentDto>> commentsOrderedByDate(){
		return ResponseEntity.status(HttpStatus.OK).body(iCommentService.commentsOrderedByDate());
	}


	@Autowired
	public CommentController(MessageSource message, JwtFilter jwtFilter, JwtProvider jwtProvider, ICommentService iComment) {
		this.message = message;
		this.jwtFilter = jwtFilter;
		this.jwtProvider = jwtProvider;
		this.iComment = iComment;
	}


	@PostMapping
	public ResponseEntity<Object> addComment(@RequestBody @Valid CommentCreationDto dto, HttpServletRequest request){
		try {
			String token = jwtFilter.getToken(request);
			String email = jwtProvider.getEmailFromToken(token);
			return ResponseEntity.status(HttpStatus.CREATED).body(iComment.createComment(email,dto));
		}catch(NotAcceptableException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.getMessage("comment.error.create", null, Locale.getDefault()));
		}
	}	

}
