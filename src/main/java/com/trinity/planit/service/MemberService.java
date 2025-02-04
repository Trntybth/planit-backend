package com.trinity.planit.service;

import com.trinity.planit.model.Member;
import com.trinity.planit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(String username, Member updatedMember) {
        Member existingMember = memberRepository.findByUsername(username);

        if (existingMember != null) {
            boolean updated = false; // Track if any updates were made

            if (updatedMember.getName() != null && !updatedMember.getName().isBlank()) {
                existingMember.setName(updatedMember.getName());
                updated = true;
            }
            if (updatedMember.getPhone() != null && !updatedMember.getPhone().isBlank()) {
                existingMember.setPhone(updatedMember.getPhone());
                updated = true;
            }

            if (updated) {
                return memberRepository.save(existingMember);
            } else {
                throw new IllegalArgumentException("No valid fields provided for update.");
            }
        }

        throw new NoSuchElementException("Member not found with username: " + username);
    }



}