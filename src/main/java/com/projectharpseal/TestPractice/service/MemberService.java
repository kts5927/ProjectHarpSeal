package com.projectharpseal.TestPractice.service;

import com.projectharpseal.TestPractice.dto.MemberResponseDto;

import java.util.List;

public interface MemberService {

    List<MemberResponseDto.ListDto> findAll();
    Long createMember(String name, int age);
}