package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.request.MemberCreationDto;
import com.alkemy.ong.dto.response.MemberResponseDto;
import com.alkemy.ong.model.Member;

import java.util.List;

public interface IMemberService {

    List<MemberResponseDto> showAllMembers();

    MemberResponseDto createMember(MemberCreationDto member);

    MemberResponseDto updateMemberById(Long id, MemberCreationDto dto);

    String deleteMember(Long id);

    Member getMemberById(Long id);
}
