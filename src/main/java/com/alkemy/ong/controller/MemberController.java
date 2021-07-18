package com.alkemy.ong.controller;

import com.alkemy.ong.dto.request.MemberCreationDto;
import com.alkemy.ong.dto.response.MemberResponseDto;
import com.alkemy.ong.service.Interface.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import java.util.Locale;


@RestController
@RequestMapping("/members")
public class MemberController {

    private final IMemberService iMemberService;
    private final MessageSource messageSource;

    @Autowired
    public MemberController(IMemberService iMemberService, MessageSource messageSource) {
        this.iMemberService = iMemberService;
        this.messageSource = messageSource;
    }


    @GetMapping
    public ResponseEntity<?> getAllMembers(@PageableDefault(size = 10, page = 0) Pageable pageable, @RequestParam(value = "page", defaultValue = "0") int page){
    	try {
    		Page<?> result = iMemberService.showAllMembers(pageable);
    		if(page >= result.getTotalPages()) {
    			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("pagination.error.notFound", null,Locale.getDefault()));
    		}
    		return ResponseEntity.ok(result);
    	}catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    	}

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
	public ResponseEntity<?> updateMember(@PathVariable("id") Long id, @Valid @ModelAttribute(name = "memberCreationDto") MemberCreationDto memberCreationDto)
    {
		try {
			return new ResponseEntity<>(iMemberService.updateMemberById(id, memberCreationDto), HttpStatus.OK);
		} catch(Exception e) {
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

