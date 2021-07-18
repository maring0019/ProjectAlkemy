package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.request.CommentCreationDto;
import com.alkemy.ong.dto.response.CommentResponseDto;
import com.alkemy.ong.exception.CommentNotFoundException;

public interface ICommentService {

	CommentResponseDto createComment(String email,CommentCreationDto dto);

	CommentResponseDto updateComment(Long id, CommentCreationDto comment) throws CommentNotFoundException;
}
