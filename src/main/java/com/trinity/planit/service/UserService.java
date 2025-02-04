package com.trinity.planit.service;

import com.trinity.planit.model.Member;
import com.trinity.planit.model.Organisation;
import com.trinity.planit.model.User;
import com.trinity.planit.repository.MemberRepository;
import com.trinity.planit.repository.OrganisationRepository;
import com.trinity.planit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationRepository organisationRepository; // Assuming you have a repository for Organisation

    // delete user for either organisation or member
    public boolean deleteUserByUsername(String username) {
        // Try deleting a member first
        if (memberRepository.existsByUsername(username)) {
            memberRepository.deleteByUsername(username);
            return true;
        }

        // Try deleting an organisation if member not found
        if (organisationRepository.existsByUsername(username)) {
            organisationRepository.deleteByUsername(username);
            return true;
        }

        return false;  // return false if neither was found
    }

    // find user for either organisation or member
    public Object findUserByUsername(String username) {
        // check if it's a member
        Member member = memberRepository.findByUsername(username);
        if (member != null) {
            return member;
        }

        // check if it's an organisation
        Organisation organisation = organisationRepository.findByUsername(username);
        if (organisation != null) {
            return organisation;
        }

        return null; // return null if user is not found
    }



}
