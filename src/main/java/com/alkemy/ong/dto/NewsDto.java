package com.alkemy.ong.dto;

import com.alkemy.ong.model.Categories;
import lombok.Data;

import java.io.Serializable;

@Data
public class NewsDto  implements Serializable {

    private static final long serialVersionUID = 6522896498689132123L;

    private Long id;
    private String name;
    private String content;
    private String image;
    private Categories category;
}
