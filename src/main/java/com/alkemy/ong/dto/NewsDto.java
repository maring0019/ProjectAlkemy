package com.alkemy.ong.dto;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class NewsDto  implements Serializable {

    private static final long serialVersionUID = 6522896498689132123L;

    private Long id;
    @NotBlank(message = "Complete el campo nombre")
    @Size(min = 2, max = 20, message = "El nombre debe contener entre 2 y 20 caracteres")
    private String name;
    @NotBlank(message = "Complete el campo contenido")
    private String content;
    @NotBlank(message = "Complete el campo imagen")
    private String image;
    /*private CategoryDto category;*/
    private String categoryId;
    private boolean deleted;
}
