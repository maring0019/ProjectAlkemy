package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.model.Member;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMemberService {

    public Page<Member> showAllMembers(Pageable pageable);

    public Member createMember(Member member);

    public MemberDto updateMemberById(Long id, MemberDto dto);

    public Member getById(Long id);
}
