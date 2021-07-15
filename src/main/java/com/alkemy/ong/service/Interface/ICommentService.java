package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.CommentDto;

public interface ICommentService {

	public CommentDto createComment(String email,CommentDto dto);
}
