package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.exception.CommentNotFoundException;

public interface ICommentService {

	public CommentDto createComment(String email,CommentDto dto);

	CommentDto updateComment(Long id, CommentDto comment) throws CommentNotFoundException;
}
