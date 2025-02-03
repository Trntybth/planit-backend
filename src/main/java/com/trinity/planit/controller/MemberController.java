package com.trinity.planit.controller;

import com.trinity.planit.model.Member;
import com.trinity.planit.repository.MemberRepository;
import com.trinity.planit.service.MemberService;
import com.trinity.planit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;


    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{name}")
    public List<Member> getMembersByName(@PathVariable String name) {
        return memberService.getMembersByName(name);
    }

    @GetMapping("/{username}")
    public List<Member> getMembersByUsername(@PathVariable String username) {
        return memberService.getMembersByUsername(username);
    }

    @PostMapping
    public Member addMember(@RequestBody Member member) {
        return memberService.addMember(member);
    }



}


