package com.alkemy.ong.repository;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer>{


    @Query("SELECT c.body FROM Comment c")
    List<CommentDto> findAllByOrderCreatedDesc();


    @Query(value = "SELECT body FROM comments", countQuery = "SELECT COUNT(*) FROM comments", nativeQuery = true)
    List<CommentResponseDto> findAllByOrderCreatedDesc();

}

