package com.trinity.planit.controller;

import com.trinity.planit.model.Member;
import com.trinity.planit.service.MemberService;
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
    public Member getMemberByUsername(@PathVariable String username) {
        return memberService.getMemberByUsername(username);
    }

    @PostMapping
    public Member addMember(@RequestBody Member member) {
        return memberService.addMember(member);
    }


    @PutMapping("/{username}")
    public ResponseEntity<Member> updateMember(@PathVariable String username, @RequestBody Member updatedMember) {
        Member member = memberService.updateMember(username, updatedMember);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


}

