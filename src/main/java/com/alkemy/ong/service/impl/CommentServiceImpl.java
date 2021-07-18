package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.Interface.ICommentService;
import com.alkemy.ong.service.Interface.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class CommentServiceImpl {
    @Autowired
    private CommentRepository commentRepository;


    public List<CommentDto> commentsOrderedByDate() {return commentRepository.findAllByOrderCreatedDesc();}

}
