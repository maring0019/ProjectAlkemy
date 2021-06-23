package com.alkemy.ong.model;

import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "activities")
@SQLDelete(sql = "UPDATE activities SET deleted = true WHERE id=?")
@FilterDef(name = "deletedActivitiesFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean")) //Define los requerimientos, los cuales, serán usados por @Filter
@Filter(name = "deletedActivitiesFilter", condition = "deleted = :isDeleted") //Condición para aplicar el filtro en función del parámetro
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ActivitiesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    private boolean deleted = Boolean.FALSE;

    @Column(name = "created_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;

}
