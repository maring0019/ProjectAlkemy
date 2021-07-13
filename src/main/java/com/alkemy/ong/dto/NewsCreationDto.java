package com.alkemy.ong.dto;

import com.alkemy.ong.model.Categories;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter @Setter
public class NewsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String content;
    private MultipartFile image;
    private Long category;

}
