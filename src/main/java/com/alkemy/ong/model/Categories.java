package com.alkemy.ong.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@SQLDelete(sql = "UPDATE categories SET deleted=true WHERE id =?")
@Where(clause = "deleted = false")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categories implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "El nombre no puede estar vacío")
	private String name;

	private String description;

	private String image;

	@Column(name = "created_date_time", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date edited;

	private Boolean deleted = Boolean.FALSE;

}
