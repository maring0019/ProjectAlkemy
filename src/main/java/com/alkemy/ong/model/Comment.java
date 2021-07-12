package com.alkemy.ong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name ="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"comments", "handler","hibernateLazyInitializer"}, allowSetters = true)
    @JoinColumn(name = "users_id")
    private User user;

    @NotBlank(message = "El campo body no puede estar vacío.")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"comments", "handler","hibernateLazyInitializer"}, allowSetters = true)
    @JoinColumn(name = "news_id")
    private News news;


}