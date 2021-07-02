package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class OrganizationDtoComp {

    private String name;

    private String image;

    private Integer phone;

    private String address;
    
    private String welcomeText;
    
    private String aboutUsText;
}
