package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "organizations")
@SQLDelete(sql = "UPDATE Organizations SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Organization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false ,length = 80)
    @NotNull(message = "El campo name no puede estar vacío")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "El campo image no puede estar vacío")
    private String image;

    private String address;

    private Integer phone;

    @Column(nullable = false ,length = 80)
    @NotNull(message = "El campo email no puede estar vacío")
    @Email(message = "El formato no es válido")
    private String email;


    @NotNull(message = "El campo welcomeText no puede estar vacío")
    @Column(columnDefinition = "TEXT",nullable = false)
    private String welcomeText;

    @Column(columnDefinition = "TEXT")
    private String aboutUsText;

    private boolean deleted = Boolean.FALSE;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;
}
