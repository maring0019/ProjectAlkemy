package com.alkemy.ong.service.impl;

import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.Interface.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> showAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }
}
