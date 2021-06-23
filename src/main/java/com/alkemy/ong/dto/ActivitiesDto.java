package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Getter @Setter
public class ActivitiesDto implements Serializable {

    private Long id;

    @NotBlank(message = "El nombre es requerido.")
    private String name;

    @NotBlank(message = "El contenido es requerido.")
    private String content;

    @NotBlank(message = "La imágen es requerido.")
    private String image;

    private Date created;

    private Date edited;
}
