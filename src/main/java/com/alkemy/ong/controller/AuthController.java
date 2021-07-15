package com.alkemy.ong.controller;

import javax.json.JsonPatch;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.alkemy.ong.dto.LoginUsersDto;
import com.alkemy.ong.exception.NotRegisteredException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.UsersDto;
import com.alkemy.ong.security.JwtFilter;
import com.alkemy.ong.security.JwtProvider;
import com.alkemy.ong.service.Interface.IUsersService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Api(value = "Auth controller")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private final IUsersService usersService;
    @Autowired
    private final JwtProvider jwtProvider;
    @Autowired
    private final JwtFilter jwtFilter;


    @PostMapping(path = "/register")
    @ApiOperation("Registro de usuarios.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuario registrado con  éxito."),
            @ApiResponse(code = 400, message = "Error no se pudo realizar el registro.")
    })
    public ResponseEntity<Object> createUser(@Valid @RequestBody UsersDto usersDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usersService.createUser(usersDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "/login")
    @ApiOperation("Inicio de sesion.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Inicio de sesión exitosa."),
            @ApiResponse(code = 404, message = "Error al querer iniciar sesión.")
    })
    public ResponseEntity<String> loginUser(@RequestBody LoginUsersDto credentials){
        try {
            return ResponseEntity.ok(usersService.loginUser(credentials));
        } catch (NotRegisteredException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "/users/{id}", consumes = "application/json-patch+json")
    @ApiOperation("Edicion de usuario.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Edición exitosa."),
            @ApiResponse(code = 404, message = "Usuario no encontrado.")
    })
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody JsonPatch patch) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(usersService.patchUpdate(id, patch));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @GetMapping("/me")
    @ApiOperation("Buscar mi sesion.")
    @ApiResponses({
            @ApiResponse(code = 302, message = "Operación exitosa."),
            @ApiResponse(code = 404, message = "error sesión no encontrada.")
    })
    public ResponseEntity<Object> userInfo(HttpServletRequest request){
        try {
            String token = jwtFilter.getToken(request);
            String email = jwtProvider.getEmailFromToken(token);

            return ResponseEntity.status(HttpStatus.FOUND)
            		.body(usersService.getUser(email));

        }catch(UsernameNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
