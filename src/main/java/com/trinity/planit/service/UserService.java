package com.trinity.planit.service;

import com.trinity.planit.model.Member;
import com.trinity.planit.model.Organisation;
import com.trinity.planit.model.User;
import com.trinity.planit.repository.MemberRepository;
import com.trinity.planit.repository.OrganisationRepository;
import com.trinity.planit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<User> findAllUsers() {
        return userRepository.findAll();
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

    public Organisation findOrCreateOrganisation(String email, String name, String picture) {
        // Check if the user already exists in the database
        Organisation existingUser = findOrganisationByEmail(email);
        if (existingUser != null) {
            return existingUser;
        } else {
            // Create and return a new Organisation
            // Optionally save to the database here
            return new Organisation(email, name, picture);
        }
    }

    public Member findOrCreateMember(String email, String name, String picture) {
        // Check if the user already exists in the database
        Member existingUser = findMemberByEmail(email);
        if (existingUser != null) {
            return existingUser;
        } else {
            // Create and return a new Member
            // Optionally save to the database here
            return new Member(email, name, picture);
        }
    }

    // Example methods to find users by email (you can replace this with actual database queries)
    private Organisation findOrganisationByEmail(String email) {
        // Placeholder for actual database lookup
        return null; // Return null if not found
    }

    private Member findMemberByEmail(String email) {
        // Placeholder for actual database lookup
        return null; // Return null if not found
    }

/*    public User getUserByEmail(String email) {
        // Try to find the member first
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            return member;  // Return member if found
        }

        // If no member, try to find the organisation
        Organisation organisation = organisationRepository.findByEmail(email);
        if (organisation != null) {
            return organisation;  // Return organisation if found
        }

        // If no user found, return null (or handle differently if needed)
        return null;
    }*/
}
