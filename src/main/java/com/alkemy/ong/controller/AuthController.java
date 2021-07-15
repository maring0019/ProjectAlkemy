package com.alkemy.ong.controller;

import javax.json.JsonPatch;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.alkemy.ong.dto.request.LoginUsersDto;
import com.alkemy.ong.dto.response.UserResponseDto;
import com.alkemy.ong.exception.NotRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.request.UsersCreationDto;
import com.alkemy.ong.security.JwtFilter;
import com.alkemy.ong.security.JwtProvider;
import com.alkemy.ong.service.Interface.IUsersService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUsersService usersService;
    private final JwtProvider jwtProvider;
    private final JwtFilter jwtFilter;
    private final ProjectionFactory projectionFactory;

    @Autowired
    public AuthController(IUsersService usersService, JwtProvider jwtProvider, JwtFilter jwtFilter, ProjectionFactory projectionFactory) {
        this.usersService = usersService;
        this.jwtProvider = jwtProvider;
        this.jwtFilter = jwtFilter;
        this.projectionFactory = projectionFactory;
    }


    @PostMapping(path = "/register")
    public ResponseEntity<Object> createUser(@Valid @ModelAttribute(name = "usersCreationDto") UsersCreationDto usersCreationDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usersService.createUser(usersCreationDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUsersDto credentials){
        try {
            return ResponseEntity.ok(usersService.loginUser(credentials));
        } catch (NotRegisteredException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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



    @GetMapping("/me")
    public ResponseEntity<Object> userInfo(HttpServletRequest request){
        try {
            String token = jwtFilter.getToken(request);
            String email = jwtProvider.getEmailFromToken(token);
            return ResponseEntity.status(HttpStatus.OK)
            		.body(projectionFactory.createProjection(UserResponseDto.class, usersService.getUser(email)));
        }catch(UsernameNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
