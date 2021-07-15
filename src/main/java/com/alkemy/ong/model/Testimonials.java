package com.alkemy.ong.model;

import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "testimonials")
@SQLDelete(sql = "UPDATE testimonials SET deleted = true where id=?")
@FilterDef(name = "deletedTestimonialsFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedTestimonialsFilter", condition = "deleted = :isDeleted")
@NoArgsConstructor
@Getter @Setter
public class Testimonials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "El campo Nombre no debe estar vacío")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    private String image;

    @NotBlank(message = "El campo Content no debe estar vacío")
    @Column(name = "content")
    private String content;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_edited")
    private Date edited;

    private Boolean deleted = Boolean.FALSE;

    public Testimonials(String name, String content) {
        this.name = name;
        this.content = content;
        this.created = new Date();
    }
}
