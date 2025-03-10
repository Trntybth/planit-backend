package com.trinity.planit.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.trinity.planit.model.GoogleAuthRequest;
import com.trinity.planit.model.Member;
import com.trinity.planit.model.Organisation;
import com.trinity.planit.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody GoogleAuthRequest authRequest) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance()
            )
                    .setAudience(Collections.singletonList("YOUR_WEB_CLIENT_ID"))
                    .build();

            GoogleIdToken googleIdToken = verifier.verify(authRequest.getIdToken());
            if (googleIdToken != null) {
                GoogleIdToken.Payload payload = googleIdToken.getPayload();

                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String picture = (String) payload.get("picture");

                // decide if user should be an Organisation or Member
                if ("ORGANISATION".equalsIgnoreCase(authRequest.getUserType())) {
                    Organisation user = userService.findOrCreateOrganisation(email, name, picture);
                    return ResponseEntity.ok(user);
                } else if ("MEMBER".equalsIgnoreCase(authRequest.getUserType())) {
                    Member user = userService.findOrCreateMember(email, name, picture);
                    return ResponseEntity.ok(user);
                } else {
                    return ResponseEntity.badRequest().body("Invalid user type");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }
}
