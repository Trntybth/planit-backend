package com.trinity.planit.service;

import com.trinity.planit.model.Member;
import com.trinity.planit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public List<Member> getMembersByName(String name) {
        return memberRepository.findByName(name);
    }

    public List<Member> getMembersByUsername(String username) {
        return memberRepository.findByName(username);
    }

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }




}