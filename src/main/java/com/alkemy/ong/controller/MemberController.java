package com.alkemy.ong.controller;

import com.alkemy.ong.model.Member;
import com.alkemy.ong.service.Interface.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping()
public class MemberController {

    @Autowired
    private IMemberService iMemberService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/members")
    public ResponseEntity< List<Member> > getAllMembers(){
        return ResponseEntity.status(HttpStatus.OK).body(iMemberService.showAllMembers());
    }

    @PostMapping("/members")
    public ResponseEntity<?> create(@Validated @RequestBody Member member){
        try {
            iMemberService.createMember(member);
            return ResponseEntity.status(HttpStatus.CREATED).body(messageSource.getMessage("new.create.successful",
                    null,Locale.getDefault()));
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
