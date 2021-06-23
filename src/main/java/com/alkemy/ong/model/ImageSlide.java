package com.alkemy.ong.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NotBlank
    private String imageUrl;
    private String text;
    @NotNull
    private Long ordered;
    @NotNull
    private Long organizationId;
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
