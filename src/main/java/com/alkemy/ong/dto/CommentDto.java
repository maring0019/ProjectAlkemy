package com.alkemy.ong.dto;

import com.alkemy.ong.model.News;
import com.alkemy.ong.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class CommentDto {
    @NotBlank(message = "El campo body no puede estar vacío.")
    private String body;


    private User user;


    private News news;

    private Date createDate;

}
