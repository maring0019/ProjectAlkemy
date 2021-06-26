package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter
public class ActivitiesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String content;

    private String image;

    private Date created;

    private Date edited;

    private Date deletedAt;
}
