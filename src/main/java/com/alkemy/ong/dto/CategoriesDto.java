package com.alkemy.ong.dto;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDto {

	@Id
	private Long id;

	private String name;

	private String description;

	private String image;

	@NonNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date created = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date edited;

	private Boolean deleted = Boolean.FALSE;
}
