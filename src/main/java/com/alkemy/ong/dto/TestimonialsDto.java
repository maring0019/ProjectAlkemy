package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class TestimonialsDto {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String image;

    @Getter @Setter
    private String content;

    @Getter @Setter
    private Date created_date;

    @Getter @Setter
    private Date edited_date;

    @Getter @Setter
    private Boolean deleted=false;

}
