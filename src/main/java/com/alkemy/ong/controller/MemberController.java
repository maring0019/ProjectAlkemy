package com.alkemy.ong.controller;

import com.alkemy.ong.dto.request.MemberCreationDto;
import com.alkemy.ong.dto.response.MemberResponseDto;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.service.Interface.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("/members")
public class MemberController {

    private final IMemberService iMemberService;

    @Autowired
    public MemberController(IMemberService iMemberService) {
        this.iMemberService = iMemberService;
    }

    @GetMapping
    public ResponseEntity< List<MemberResponseDto> > getAllMembers(){
        return ResponseEntity.status(HttpStatus.OK).body(iMemberService.showAllMembers());
    }

    @PostMapping
    public ResponseEntity<?> createMember(@Valid @ModelAttribute(name = "memberCreationDto") MemberCreationDto memberCreationDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(iMemberService.createMember(memberCreationDto));
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @PutMapping(path = "/{id}")
	public ResponseEntity<?> updateMember(@PathVariable("id") Long id, @Valid @ModelAttribute(name = "memberCreationDto") MemberCreationDto memberCreationDto) {
		try {
			return new ResponseEntity<>(iMemberService.updateMemberById(id, memberCreationDto), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<String> deleteMember(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(iMemberService.deleteMember(id), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
