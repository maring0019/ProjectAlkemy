package com.alkemy.ong.model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="testimonials")
@SQLDelete(sql = "UPDATE testimonials SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class TestimonialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotEmpty(message = "Name no puede estar vacio")
    private String name;
    @Column(nullable = false)
    @NotEmpty(message = "Image no puede estar vacio")
    private String image;
    @Column(nullable = false)
    @NotEmpty(message = "Content no puede estar vacio")
    private String content;

    private boolean deleted = Boolean.FALSE;

    public TestimonialsEntity() {
    }

    public TestimonialsEntity(Long id,@NotEmpty String name, @NotEmpty String image,@NotEmpty String content, boolean deleted) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.content = content;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "TestimonialsEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
