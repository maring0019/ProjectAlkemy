package com.alkemy.ong.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.*;

@Getter @Setter
public class CategoriesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String description;

	private String image;

	private Date created;

	private Date edited;

	private Boolean deleted;
}
