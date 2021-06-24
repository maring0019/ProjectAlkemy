package com.alkemy.ong.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity(name = "users")
@Getter @Setter
@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class UsersEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "El Nombre es requerido.")
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotBlank(message = "El Apellido es requerido.")
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@NotBlank(message = "El Email es requerido.")
	@Email(message = "Email invalido.")
	@Column(nullable = false)
	private String email;
	
	@NotBlank(message = "La contraseña es requerida.")
	@Size(min = 8, max = 15)
	private String password;

	@Column(nullable = true)
	private String photo;
	
	@Column(name = "create_date", updatable = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name = "edited_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date edited;
	
	@ManyToMany
	@JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "users_id"),
	inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles = new HashSet<>();
	
	private Boolean deleted = Boolean.FALSE;

	@Builder
	public UsersEntity(String firstName, String lastName, String email,
			@NotBlank(message = "Password is required.") String password, String photo) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.photo = photo;
	}
	

	


}
