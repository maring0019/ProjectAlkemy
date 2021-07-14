package com.alkemy.ong.service.Interface;

import com.alkemy.ong.model.Member;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMemberService {

    public Page<Member> showAllMembers(Pageable pageable);

    public Member createMember(Member member);
    
}
