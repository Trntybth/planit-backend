package com.trinity.planit.controller;

import com.trinity.planit.model.Event;
import com.trinity.planit.model.EventSignUp;
import com.trinity.planit.model.SignUpRequest;
import com.trinity.planit.repository.EventRepository;
import com.trinity.planit.repository.EventSignUpRepository;
import com.trinity.planit.service.EventSignUpService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events/signup")
public class EventSignUpController {

    @Autowired
    private EventSignUpRepository eventSignUpRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventSignUpService eventSignUpService;

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);


    // Helper method to check if the eventId is valid ObjectId
    private boolean isValidObjectId(String eventId) {
        try {
            new ObjectId(eventId); // Try converting to ObjectId
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @GetMapping("/byEventId/{eventId}")
    public ResponseEntity<List<EventSignUp>> getEventSignUpsByEventId(@PathVariable String eventId) {
        if (!ObjectId.isValid(eventId)) {
            return ResponseEntity.badRequest().body(null);  // Or a better error response
        }

        List<EventSignUp> signUps = eventSignUpService.getSignUpsByEventId(new ObjectId(eventId));

        return ResponseEntity.ok(signUps);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUpForEvent(@RequestParam String memberEmail, @RequestParam String eventId, @RequestBody SignUpRequest signUpRequest) {
        logger.debug("Signup request: memberEmail={}, eventId={}, signup={}", memberEmail, eventId, signUpRequest.isSignup());

        if (!ObjectId.isValid(eventId)) {
            logger.warn("Invalid event ID format: {}", eventId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event ID format.");
        }

        ObjectId eventObjectId = new ObjectId(eventId);

        Optional<EventSignUp> existingSignupOpt = eventSignUpRepository.findByMemberEmailAndEventId(memberEmail, eventObjectId);

        EventSignUp eventSignup;

        if (existingSignupOpt.isPresent()) {
            eventSignup = existingSignupOpt.get();

            if (eventSignup.isSignup()) {
                logger.warn("User already signed up: {}", memberEmail);
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Already signed up.");
            } else {
                eventSignup.setSignup(true);
                eventSignUpRepository.save(eventSignup);
                logger.info("Signup reactivated for member: {}", memberEmail);
                return ResponseEntity.ok("Successfully reactivated your signup.");
            }

        } else {
            eventSignup = new EventSignUp(memberEmail, eventObjectId, true);
            eventSignUpRepository.save(eventSignup);
            logger.info("Signup successful for member: {}", memberEmail);
            return ResponseEntity.ok("Successfully signed up.");
        }
    }


    @PostMapping("/un-sign-up")
    public ResponseEntity<String> unSignUpFromEvent(@RequestParam String memberEmail, @RequestParam String eventId) {
        logger.debug("Received un-sign-up request for memberEmail: {} and eventId: {}", memberEmail, eventId);

        if (!ObjectId.isValid(eventId)) {
            logger.warn("Invalid event ID format: {}", eventId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event ID format.");
        }

        ObjectId eventObjectId = new ObjectId(eventId);

        try {
            // Find the signup in the database explicitly matching memberEmail and eventId, and signup=true
            Optional<EventSignUp> eventSignupOpt = eventSignUpRepository
                    .findByMemberEmailAndEventIdAndSignupTrue(memberEmail, eventObjectId);

            if (eventSignupOpt.isPresent()) {
                EventSignUp eventSignup = eventSignupOpt.get();

                // Clearly set signup to false and save
                eventSignup.setSignup(false);
                eventSignUpRepository.save(eventSignup);

                logger.info("Successfully un-signed up member: {} for event: {}", memberEmail, eventId);
                return ResponseEntity.ok("You have successfully un-signed up.");
            } else {
                logger.warn("Signup not found or already un-signed up for member: {}, eventId: {}", memberEmail, eventId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Signup not found or already un-signed up.");
            }

        } catch (Exception e) {
            logger.error("Error while un-signing up for memberEmail: {}, eventId: {}", memberEmail, eventId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing your un-sign up request.");
        }
    }


    @GetMapping("/signed-up-events")
    public ResponseEntity<List<Event>> getSignedUpEvents(@RequestParam String memberEmail) {
        // Retrieve all sign-ups for the member where signup is true
        List<EventSignUp> signups = eventSignUpRepository.findByMemberEmailAndSignupTrue(memberEmail);

        // Check if there are no signups
        if (signups.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        // Initialize a list to hold the events
        List<Event> events = new ArrayList<>();

        // Loop through the sign-ups and retrieve the event for each
        for (EventSignUp signup : signups) {
            try {
                // Convert eventId (ObjectId) to a string
                ObjectId eventObjectId = signup.getEventId();

                // Find the event by its ObjectId
                Optional<Event> event = eventRepository.findById(eventObjectId.toString());

                // If the event is found, add it to the list
                event.ifPresent(events::add);
            } catch (Exception e) {
                logger.error("Error occurred while retrieving event for signup: {}", signup.getId(), e);
            }
        }

        // If no events found, return a not found response
        if (events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        return ResponseEntity.ok(events);
    }

}

