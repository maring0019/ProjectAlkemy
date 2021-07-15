package com.alkemy.ong.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
@ApiModel(description = "Modelo de categories")
@Data
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class CategoriesNameDto {

    @ApiModelProperty(notes ="Nombre de categoria")
    private String name;
}
