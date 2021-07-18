package com.alkemy.ong.service.Interface;


import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import com.alkemy.ong.dto.request.CommentCreationDto;
import com.alkemy.ong.dto.response.CommentResponseDto;

public interface ICommentService {
    List<CommentDto> commentsOrderedByDate();

	CommentResponseDto createComment(String email, CommentCreationDto dto);
}

