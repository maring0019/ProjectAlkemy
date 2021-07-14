package com.alkemy.ong.dto;

import com.alkemy.ong.model.Categories;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter @Setter
public class NewsCreationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    @NotBlank(message = "Completar el campo contenido")
    private String content;

    @NotNull(message = "La imagen es requerida.")
    private MultipartFile image;

    private Long category;

}
