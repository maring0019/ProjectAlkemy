package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.CommentNotFoundException;
import com.alkemy.ong.util.RoleValidator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.CommentDto;
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
	public CommentDto createComment(String email, CommentDto dto) {
		
		User user = repoUser.findByEmail(email).get();
		News post = repoNew.findById(dto.getIdNews()).get();
		
		Comment comment = new Comment();
		comment.setNews(post);
		comment.setUser(user);
		comment.setBody(dto.getBody());
		
		
		return mapper.map(repoComment.save(comment) , CommentDto.class);
	}

	@Override
	public CommentDto updateComment(Long id, CommentDto comment, String token) throws CommentNotFoundException {
		Comment foundComment = repoComment.findById(id).orElseThrow(() -> new CommentNotFoundException(
				messageSource.getMessage("comment.error.not.found", null, Locale.getDefault())
		));
		if(foundComment!=null)
			foundComment.setBody(comment.getBody());
		return mapper.map(repoComment.save(foundComment), CommentDto.class);
	}


}
