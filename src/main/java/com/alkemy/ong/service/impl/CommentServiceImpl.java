package com.alkemy.ong.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.News;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.UsersRepository;
import com.alkemy.ong.service.Interface.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService{

	@Autowired
	private UsersRepository repoUser;
	@Autowired
	private NewsRepository repoNew;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private CommentRepository repoComment;
	
	
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

	
}
