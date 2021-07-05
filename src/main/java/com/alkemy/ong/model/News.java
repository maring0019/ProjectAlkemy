package com.alkemy.ong.model;


import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "news")
@SQLDelete(sql = "UPDATE NewsEntity SET deleted = true WHERE id=?")
@FilterDef(name = "deletedNewsFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Completar el campo nombre")
    @Size(min = 2, max = 20, message = "El nombre debe contener entre 2 y 20 carácteres")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Completar el campo contenido")
    private String content;

    @Column(nullable = false)
    @NotBlank(message = "Completar el campo imagen")
    private String image;

    @OneToOne
    @JoinColumn(name="category")
    private Categories category;

    private boolean deleted = Boolean.FALSE;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date_time", nullable = false, updatable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;

    @Builder
    public News(String name, String content, String image, Categories category) {
        super();
        this.name = name;
        this.content = content;
        this.image = image;
        this.category = category;
        this.created = new Date();
    }
}
