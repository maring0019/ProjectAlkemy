package com.alkemy.ong.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.alkemy.ong.model.Role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@ApiModel(description = "Detalles sobre usuario")
public class UsersDto implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Identificación única del usuario.")
	private Long id;

	@ApiModelProperty(notes = "Nombre del usuario.")
	@NotBlank
	private String firstName;

	@ApiModelProperty(notes = "Apellido del usuario.")
	@NotBlank
	private String lastName;

	@ApiModelProperty(notes = "Email del usuario.")
	@NotBlank
	@Email
	private String email;

	@ApiModelProperty(notes = "Password del usuario.")
	@NotBlank
	@Size(min = 8 , message = "La contraseña debe tener más de 8 caracteres.")
	private String password;

	@ApiModelProperty(notes = "Foto del usuario.")
	private String photo;

	private Date created;

	private Date edited;

	private Set<Role> roles;

	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
