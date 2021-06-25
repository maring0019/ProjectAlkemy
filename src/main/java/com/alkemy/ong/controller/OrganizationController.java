package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDto;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrganizationController {

    @RequestMapping(value = "/organization/public", method = RequestMethod.GET)
    public ResponseEntity<?> getOrganizationData(){

    }
}
