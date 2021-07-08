package com.alkemy.ong.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = " UPDATE image_slide SET deleted = TRUE WHERE id = ?")
@Where(clause = "deleted = false")
public class ImageSlide implements Comparable<ImageSlide>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "La url de imagen no puede estar vacía")
    private String imageUrl;
    private String text;
    @NotNull(message = "Es necesario un valor entero para ordenar las imágenes")
    private Long ordered;
    @NotNull(message = "Es necesaria una Id de organización")
    private Long organizationId;
    
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    private boolean deleted = false;

    public ImageSlide(String imageUrl, String text, Long ordered, Long organizationId, Date createdAt) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.ordered = ordered;
        this.organizationId = organizationId;
        this.createdAt = createdAt;
    }

    @Override
    public int compareTo(ImageSlide o) {
        if(this.ordered.equals(o.getOrdered()) && this.getCreatedAt().equals(o.getCreatedAt()))
            return 0;
        else if(this.ordered > o.getOrdered() && createdAt.before(o.getCreatedAt()))
            return 1;
        else
            return -1;
    }

}
