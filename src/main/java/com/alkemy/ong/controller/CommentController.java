package com.alkemy.ong.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.security.JwtFilter;
import com.alkemy.ong.security.JwtProvider;
import com.alkemy.ong.service.Interface.ICommentService;
import com.amazonaws.services.lexruntime.model.NotAcceptableException;


@RestController
@RequestMapping(path = "/comments")
public class CommentController {

	@Autowired
	private MessageSource message;
	@Autowired
	private JwtFilter jwtFilter;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private ICommentService iComment;
	
	
	@PostMapping
	public ResponseEntity<Object> addComment(@RequestBody CommentDto dto, HttpServletRequest request){
		try {
			String token = jwtFilter.getToken(request);
			String email = jwtProvider.getEmailFromToken(token);
			iComment.createComment(email,dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(message.getMessage("comment.create.successful", null, Locale.getDefault()));
		}catch(NotAcceptableException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.getMessage("comment.error.create", null, Locale.getDefault()));
		}
	}	
}
