package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.request.CommentCreationDto;
import com.alkemy.ong.dto.response.CommentResponseDto;

public interface ICommentService {

	CommentResponseDto createComment(String email, CommentCreationDto dto);
}
