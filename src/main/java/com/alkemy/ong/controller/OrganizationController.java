package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.service.impl.OrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController("/organization")
public class OrganizationController {

    @Autowired
    OrganizationServiceImpl organizationService;

    @GetMapping(value = "/public")
    public ResponseEntity<?> getOrganizationData(){
        List<OrganizationDto> dto = organizationService.getAll();
        return ResponseEntity.ok(dto);
    }
    
    @PostMapping("/public")
    public ResponseEntity<?> updateOrganization(@Valid Organization organization) {
    	try {
    		
    	}
    	
    }

}

