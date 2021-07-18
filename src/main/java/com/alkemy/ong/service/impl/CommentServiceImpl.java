package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.request.CommentCreationDto;
import com.alkemy.ong.dto.response.CommentResponseDto;
import com.alkemy.ong.exception.CommentNotFoundException;
import com.alkemy.ong.util.RoleValidator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.News;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.UsersRepository;
import com.alkemy.ong.service.Interface.ICommentService;

import java.util.Locale;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements ICommentService{

	private final UsersRepository repoUser;
	private final NewsRepository repoNew;
	private final ModelMapper mapper;
	private final CommentRepository repoComment;
	private final RoleValidator validator;
	private final MessageSource messageSource;
	
	
	@Override
	public CommentResponseDto createComment(String email, CommentCreationDto dto) {
		
		User user = repoUser.findByEmail(email).get();
		News post = repoNew.findById(dto.getNews()).get();
		
		Comment comment = new Comment();
		comment.setNews(post);
		comment.setUser(user);
		comment.setBody(dto.getBody());
		
		
		return mapper.map(repoComment.save(comment) , CommentResponseDto.class);
	}

	@Override
	public CommentResponseDto updateComment(Long id, CommentCreationDto comment) throws CommentNotFoundException {
		Comment foundComment = repoComment.findById(id).orElseThrow(() -> new CommentNotFoundException(
				messageSource.getMessage("comment.error.not.found", null, Locale.getDefault())
		));
		if(foundComment!=null)
			foundComment.setBody(comment.getBody());
		return mapper.map(repoComment.save(foundComment), CommentResponseDto.class);
	}


}
