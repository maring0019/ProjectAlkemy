package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationDtoComp;
import com.alkemy.ong.service.Interface.IOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    IOrganization organizationService;

    @GetMapping(value = "/public")
    public ResponseEntity<?> getOrganizationData(){
        List<OrganizationDto> dto = organizationService.getAll();
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/public/{id}")
    public ResponseEntity<Object> getOrganizationById(@PathVariable Long id) {
    	try {
    		return ResponseEntity.status(HttpStatus.OK).body(organizationService.getById(id));
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    	}
    }
    
    @PostMapping("/public")
    public ResponseEntity<Object> updateOrganization(@Valid OrganizationDtoComp organization, @PathVariable Long id) {
        try {
        	return ResponseEntity.status(HttpStatus.OK).body(organizationService.updateOrg(id, organization));
        } catch (Exception e){
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

