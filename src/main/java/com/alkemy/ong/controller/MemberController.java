package com.alkemy.ong.controller;

import com.alkemy.ong.model.Member;
import com.alkemy.ong.service.Interface.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    private IMemberService iMemberService;

    @GetMapping("/members")
    public ResponseEntity< List<Member> > getAllMembers(){
        return ResponseEntity.status(HttpStatus.OK).body(iMemberService.showAllMembers());
    }


}
