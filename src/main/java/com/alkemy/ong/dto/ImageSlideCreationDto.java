package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter
public class ImageSlideDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private MultipartFile image;
    private String text;
    private Long organizationId;
    private Long ordered;
    private Date createdAt;

}
