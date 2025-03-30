package com.trinity.planit.controller;

import com.trinity.planit.model.User;
import com.trinity.planit.repository.MemberRepository;
import com.trinity.planit.repository.OrganisationRepository;
import com.trinity.planit.repository.UserRepository;
import com.trinity.planit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganisationRepository organisationRepository;


    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    // class for finding User of any type
    @GetMapping("/{username}")
    public ResponseEntity<Object> findUserByUsername(@PathVariable String username) {
        Object user = userService.findUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user); // Return the found user as response
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
    // class for deleting User of any type
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        if (userService.deleteUserByUsername(username)) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }


    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
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



}
