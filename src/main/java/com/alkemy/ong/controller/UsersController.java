package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UsersDto;
import com.alkemy.ong.service.Interface.IUsersService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonPatch;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

    @Autowired
    private final IUsersService usersService;

    @Autowired
    private final ModelMapper mapper;

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UsersDto usersDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usersService.createUser(usersDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody JsonPatch patch) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(usersService.patchUpdate(id, patch));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
