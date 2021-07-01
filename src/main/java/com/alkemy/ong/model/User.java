package com.alkemy.ong.model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity(name = "users")
@Getter @Setter
@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
@NoArgsConstructor
public class User implements UserDetails {

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

	@Column(nullable = false, length = 30)
	@NotBlank(message = "La contraseña es requerida.")
	private String password;

	private String photo;
	
	@Column(name = "created_date", updatable = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name = "edited_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date edited;

	@ManyToMany
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles;
	
	private Boolean deleted = Boolean.FALSE;

	@ElementCollection(targetClass=GrantedAuthority.class)
	private Collection<? extends GrantedAuthority> authorities;


	@Builder
	public User(String firstName, String lastName, String email, String photo, String password,
					 Collection<? extends GrantedAuthority> authorities) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.photo = photo;
		this.password = password;
		this.authorities = authorities;
		this.created = new Date();
	}

	public static User build(User user) {
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getName()))
				.collect(Collectors.toList());

		return new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoto(), user.getPassword(), authorities);
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
