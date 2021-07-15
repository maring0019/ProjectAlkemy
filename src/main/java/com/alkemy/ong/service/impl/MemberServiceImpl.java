package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.Interface.IMemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Page<Member> showAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @Override
    public Member createMember(Member member) {
        member.setCreateDate(new Date());
        return memberRepository.save(member);
    } 

    @Override
    public MemberDto updateMemberById(Long id, MemberDto dto) {

        Member member = getById(id);

        if(!dto.getName().isBlank())
		member.setName(dto.getName());

        if(!dto.getImage().isBlank())
		member.setImage(dto.getImage());

        if(!dto.getDescription().isBlank())
		member.setDescription(dto.getDescription());

        if(!dto.getFacebookUrl().isBlank())
		member.setFacebookUrl(dto.getFacebookUrl());

        if(!dto.getInstagramUrl().isBlank())
		member.setInstagramUrl(dto.getInstagramUrl());

        if(!dto.getLinkedinUrl().isBlank())
		member.setLinkedinUrl(dto.getLinkedinUrl());

		member.setEditDate(new Date());


		return mapper.map(memberRepository.save(member), MemberDto.class);

    }

    @Override
    public Member getById(Long id) {
        return memberRepository.findById(id).get();
    }
}
