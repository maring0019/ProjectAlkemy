package com.alkemy.ong.controller;

import com.alkemy.ong.model.Member;
import com.alkemy.ong.service.Interface.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/members")
    public ResponseEntity<Member> create(@Validated @RequestBody Member member){
        return ResponseEntity.status(HttpStatus.CREATED).body(iMemberService.createMember(member));
    }

}
