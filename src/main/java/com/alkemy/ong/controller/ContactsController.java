package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactsDto;
import com.alkemy.ong.service.Interface.IContacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/contacts")
public class ContactsController {

    @Autowired
    private IContacts contactService;

    @Autowired
    private MessageSource message;

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody ContactsDto dto){
        try {
            return ResponseEntity.ok(contactService.createContacts(dto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
