package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UsersDto;
import com.alkemy.ong.service.Interface.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUsersService iUsersService;

    @GetMapping("/users")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UsersDto> showAllUsers(){
        return iUsersService.showAllUsers();
    }

}
