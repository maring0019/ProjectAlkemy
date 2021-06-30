package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UsersDto;
import com.alkemy.ong.service.Interface.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUsersService iUsersService;

    @GetMapping("/users")
    public List<UsersDto> showAllUsers(){
        return iUsersService.showAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        try {
            iUsersService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
