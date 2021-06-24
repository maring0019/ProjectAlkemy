package com.alkemy.ong.model;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "activities")
@DynamicUpdate
@SQLDelete(sql = "UPDATE activities SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ActivitiesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "El nombre de la actividad es obligatorio.")
    @Column(nullable = false, length = 120)
    private String name;

    @NotBlank(message = "El contenido de la actividad es obligatorio.")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @NotBlank(message = "La imágen de la activdad es olbitoria.")
    @Column(nullable = false)
    private String image;

    private boolean deleted = Boolean.FALSE;

    @Column(name = "created_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;

    @Column(name = "deleted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

}
