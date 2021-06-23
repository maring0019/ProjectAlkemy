package com.alkemy.ong.model;


import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "news")
@SQLDelete(sql = "UPDATE NewsEntity SET deleted = true WHERE id=?")
@FilterDef(name = "deletedNewsFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    /*@OneToOne
    private CategoryEntity category;*/

    private boolean deleted = Boolean.FALSE;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date_time", nullable = false, updatable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;
}
