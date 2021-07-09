package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDtoComp;
import com.alkemy.ong.dto.SocialNetworkDto;
import com.alkemy.ong.service.Interface.IOrganization;
import com.amazonaws.services.workmail.model.OrganizationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    IOrganization organizationService;
    
    
    @GetMapping(value = "/public")
    public ResponseEntity<?> getOrganizationData(){
        organizationService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(organizationService.getAll());
    }
    
    @GetMapping("/public/{id}")
    public ResponseEntity<Object> getOrganizationById(@PathVariable Long id) {
    	try {
    		return ResponseEntity.status(HttpStatus.OK).body(organizationService.getById(id));
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    	}
    }
    

    @PostMapping("/public/{id}")
    public ResponseEntity<Object> updateOrganization(@Valid @RequestBody OrganizationDtoComp organization, @PathVariable Long id) {
        try {
        	return ResponseEntity.status(HttpStatus.OK).body(organizationService.updateOrg(id, organization));
        } catch (Exception e){
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PostMapping("/newContact/{id}")
    public ResponseEntity<Object> newContactOrganization(@RequestBody SocialNetworkDto contact, @PathVariable Long id){
    	try {
    		return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.newContact(id, contact));
    	}catch(OrganizationNotFoundException e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    	}
    }
    
    @PostMapping("/new")
    public ResponseEntity<Object> newOrganization(@RequestBody OrganizationDtoComp org){
    	
    	return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.newOrg(org));
    }
    

}

