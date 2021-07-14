package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.model.Member;

import java.util.List;

public interface IMemberService {

    public List<Member> showAllMembers();

    public Member createMember(Member member);

    public MemberDto updateMemberById(Long id, MemberDto dto);

    public Member getById(Long id);
}
