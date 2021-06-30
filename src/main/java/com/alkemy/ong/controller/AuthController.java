package com.alkemy.ong.controller;

import javax.json.JsonPatch;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.UsersDto;
import com.alkemy.ong.jwt.JwtFilter;
import com.alkemy.ong.jwt.JwtProvider;
import com.alkemy.ong.service.Interface.IUsersService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private final IUsersService usersService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private JwtFilter jwtFilter;


    @PostMapping(path = "/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UsersDto usersDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usersService.createUser(usersDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping(path = "/users/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody JsonPatch patch) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(usersService.patchUpdate(id, patch));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
    	try {
    		usersService.deleteUser(id);
    		return ResponseEntity.ok().build();
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    	}
    }
    
    @GetMapping("/me")
    public ResponseEntity<Object> userInfo(HttpServletRequest request){
    	try {
    		String token = jwtFilter.getToken(request);
    		String email = jwtProvider.getEmailFromToken(token);
   	
			return new ResponseEntity<>(usersService.loadUserByUsername(email), HttpStatus.FOUND);

    	}catch(UsernameNotFoundException e) {
    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    	}
    }
}
