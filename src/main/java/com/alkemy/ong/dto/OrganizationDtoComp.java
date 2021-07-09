package com.alkemy.ong.dto;

import java.util.List;

import com.alkemy.ong.model.SocialNetwork;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class OrganizationDtoComp {

	private String email;
	
    private String name;

    private String image;

    private Integer phone;

    private String address;
    
    private String welcomeText;
    
    private String aboutUsText;
    
    private List<SocialNetwork> contact;
}
