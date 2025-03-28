package com.trinity.planit.controller;

import com.trinity.planit.model.User;
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


    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkUserExists(@RequestParam("email") String email) {
        boolean exists = userRepository.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/checkEmailExists")
    public ResponseEntity<Boolean> checkEmailExists(@RequestBody String email) {
        boolean exists = userRepository.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

}
