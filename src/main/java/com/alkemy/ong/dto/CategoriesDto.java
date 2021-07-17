package com.alkemy.ong.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
@ApiModel(description = "Modelo de categories")
@Getter @Setter
public class CategoriesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Identificación única de categoria")
	private Long id;

	@ApiModelProperty(notes ="Nombre de categoria")
	private String name;

	@ApiModelProperty(notes ="Descripción de categoria")
	private String description;

	@ApiModelProperty(notes ="Imagen de categoria")
	private String image;

	@ApiModelProperty(notes ="Fecha de creación de categoria")
	private Date created;

	@ApiModelProperty(notes ="Fecha de actualización de categoria")
	private Date edited;

	@ApiModelProperty(notes ="Baja de categoria")
	private Boolean deleted;
}
