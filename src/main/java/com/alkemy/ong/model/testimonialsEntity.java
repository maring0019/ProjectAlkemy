package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class testimonialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_testimonials")
    @Getter @Setter
    private long id;

    @Column(name = "name", nullable = false, length = 50)
    @Getter @Setter
    private String name;

    @Column(name = "image", length = 100)
    @Getter @Setter
    private String image;

    @Column(name = "content")
    @Getter @Setter
    private String content;

    @Column(name = "date_created", nullable = false, updatable = false)
    @Getter @Setter
    private Date created;

    @Column(name = "date_edited", nullable = true)
    @Getter @Setter
    private Date edited;

    @Column(name = "deleted")
    @Getter @Setter
    private Boolean deleted = false;




}
