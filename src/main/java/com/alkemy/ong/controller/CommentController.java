package com.alkemy.ong.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.alkemy.ong.exception.CommentNotFoundException;
import com.alkemy.ong.exception.InvalidUserException;
import com.alkemy.ong.util.RoleValidator;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.security.JwtFilter;
import com.alkemy.ong.security.JwtProvider;
import com.alkemy.ong.service.Interface.ICommentService;
import com.amazonaws.services.lexruntime.model.NotAcceptableException;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/comments")
public class CommentController {

	private final MessageSource message;
	private final JwtFilter jwtFilter;
	private final JwtProvider jwtProvider;
	private final ICommentService iComment;
	private final RoleValidator validator;
	
	
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

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentDto comment) throws CommentNotFoundException, InvalidUserException {
		try {
			if (validator.isAuthorized()) {
				CommentDto updatedComment = iComment.updateComment(id, comment);
				return new ResponseEntity<>(updatedComment, HttpStatus.OK);
			}
		} catch (CommentNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (InvalidUserException e) {
			return ResponseEntity.status(403).build();
		}
		return ResponseEntity.internalServerError().build();
	}
}
