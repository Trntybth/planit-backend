package com.trinity.planit.controller;

import com.trinity.planit.model.ApiResponse;
import com.trinity.planit.model.Member;
import com.trinity.planit.model.Organisation;
import com.trinity.planit.model.User;
import com.trinity.planit.repository.MemberRepository;
import com.trinity.planit.repository.OrganisationRepository;
import com.trinity.planit.repository.UserRepository;
import com.trinity.planit.service.MemberService;
import com.trinity.planit.service.OrganisationService;
import com.trinity.planit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")

public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> findUserByUsername(@PathVariable String username) {
        Object user = userService.findUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        if (userService.deleteUserByUsername(username)) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @GetMapping("/check-member")
    public ResponseEntity<Boolean> checkMemberExists(@RequestParam String email, @RequestParam String username) {
        boolean exists = memberRepository.existsByEmail(email) || memberRepository.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-organisation")
    public ResponseEntity<Boolean> checkOrganisationExists(@RequestParam String email, @RequestParam String username) {
        boolean exists = organisationRepository.existsByEmail(email) || organisationRepository.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/checkEmailExists")
    public ResponseEntity<Boolean> checkEmailExists(@RequestBody String email) {
        boolean exists = userRepository.existsByEmailIgnoreCase(email);
        return ResponseEntity.ok(exists);
    }

    /**
     * ✅ Fixed: Now correctly returns 404 when no user is found.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email) {
        System.out.println("Checking for email: " + email);  // Log the input email

        // Check if it's an Organisation
        Organisation organisation = organisationService.getOrganisationByEmail(email);
        if (organisation != null) {
            return createResponse("Organisation", organisation);
        }

        // Check if it's a Member
        Member member = memberService.getMemberByEmail(email);
        if (member != null) {
            return createResponse("Member", member);
        }

        // 🔴 FIX: Return 404 instead of an empty `{}`.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with this email.");
    }

    /**
     * Ensures proper response formatting.
     */
    private ResponseEntity<Object> createResponse(String type, Object data) {
        if (data == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(type + " not found.");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("type", type);
        response.put("data", data);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/email/{email}/usertype")
    public ResponseEntity<String> getUserTypeByEmail(@PathVariable String email) {
        String userType = userService.getUserTypeByEmail(email);  // Fetch user type by email
        if (userType != null) {
            return ResponseEntity.ok(userType); // Return the user type as plain string
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); // Return a plain string error message


        }
    }
}
