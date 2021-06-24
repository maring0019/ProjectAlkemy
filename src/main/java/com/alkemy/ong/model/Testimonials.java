package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "testimonials")
@SQLDelete(sql = "UPDATE testimonials SET deleted = true where id=?")
@FilterDef(name = "deletedTestimonialsFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedTestimonialsFilter", condition = "deleted = :isDeleted")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class testimonialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_testimonials")
    private long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "content")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_edited")
    private Date edited;

    @Column(name = "deleted")
    private Boolean deleted = false;




}
