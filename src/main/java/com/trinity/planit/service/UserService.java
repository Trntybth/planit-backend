package com.trinity.planit.service;

import com.trinity.planit.repository.MemberRepository;
import com.trinity.planit.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganisationRepository organisationRepository; // Assuming you have a repository for Organisation

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

        return false;  // Return false if neither was found
    }
}
