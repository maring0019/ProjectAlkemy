package com.alkemy.ong.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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
    private Date createdAt;
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public ImageSlide(String imageUrl, String text, Long ordered, Organization organization, Date createdAt) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.ordered = ordered;
        this.organization = organization;
        this.createdAt = createdAt;
    }

    @Override
    public int compareTo(ImageSlide o) {
        if (this.ordered.equals(o.getOrdered())) {
            if (this.createdAt.before(o.getCreatedAt()))
                return 1;
            else if (this.createdAt.after(o.getCreatedAt()))
                return -1;
        } else if (this.getOrdered() > o.getOrdered())
            return -1;
        else
            return 1;
        return 0;
    }
}
