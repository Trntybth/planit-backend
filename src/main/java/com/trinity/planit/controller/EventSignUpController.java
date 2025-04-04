package com.trinity.planit.controller;

import com.trinity.planit.model.Event;
import com.trinity.planit.model.EventSignUp;
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


    @PostMapping("/sign-up")
    public ResponseEntity<String> signUpForEvent(@RequestParam String memberEmail, @RequestParam String eventId) {

        try {
            // Validate eventId format
            if (!ObjectId.isValid(eventId)) {
                logger.warn("Invalid event ID format: {}", eventId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event ID format.");
            }

            ObjectId eventObjectId = new ObjectId(eventId);
            logger.debug("Parsed eventId as ObjectId: {}", eventObjectId);
            String eventIdStr = eventObjectId.toHexString();

            // ✅ Fetch the event from the database
            Optional<Event> event = eventRepository.findById(String.valueOf(eventObjectId));
            if (event.isPresent()) {

                // Check if the member is already signed up
                Optional<EventSignUp> existingSignup = eventSignUpRepository.findByMemberEmailAndEventId(memberEmail, eventObjectId);
                if (existingSignup.isPresent()) {
                    logger.info("User already signed up: {}", memberEmail);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are already signed up for this event.");
                }

                // Create and save signup
                EventSignUp eventSignup = new EventSignUp();
                eventSignup.setMemberEmail(memberEmail);
                eventSignup.setEventId(eventObjectId); // Save eventId as a string in the database
                eventSignup.setSignup(true);
                eventSignUpRepository.save(eventSignup);

                return ResponseEntity.ok("Signed up successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
            }

        } catch (Exception e) {
            logger.error("Error during sign-up", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing the signup.");
        }
    }

    @PostMapping("/un-sign-up")
    public ResponseEntity<String> unSignUpFromEvent(@RequestParam String memberEmail, @RequestParam String eventId) {
        logger.debug("Received un-sign-up request with memberEmail: {} and eventId: {}", memberEmail, eventId);

        // Convert eventId from String to ObjectId for MongoDB query
        ObjectId eventObjectId;
        try {
            eventObjectId = new ObjectId(eventId);  // Convert eventId to ObjectId
            logger.debug("Parsed eventId as ObjectId: {}", eventObjectId);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid event ID format: {}", eventId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event ID format.");
        }

        try {
            // Log what the query is attempting to fetch from the database
            logger.debug("Attempting to find signup with memberEmail: {} and eventId: {}", memberEmail, eventObjectId);

            // Query MongoDB with memberEmail and eventId (ObjectId)
            Optional<EventSignUp> eventSignup = eventSignUpRepository.findByMemberEmailAndEventId(memberEmail, eventObjectId);

            // Log query result
            if (eventSignup.isPresent()) {
                logger.debug("Found event signup: {}", eventSignup.get());
            } else {
                logger.warn("No signup found for memberEmail: {} and eventId: {}", memberEmail, eventObjectId);
            }

            if (eventSignup.isPresent()) {
                // If the signup exists, update the signup status to false (un-sign)
                EventSignUp signup = eventSignup.get();
                logger.debug("Found event signup: {}", signup);

                // Un-sign the member (set signup to false)
                signup.setSignup(false);
                eventSignUpRepository.save(signup);
                logger.info("Successfully updated the signup status for member: {} to false", memberEmail);

                return ResponseEntity.ok("Un-signed up successfully.");
            } else {
                logger.warn("No signup found for memberEmail: {} and eventId: {}", memberEmail, eventId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You are not signed up for this event.");
            }
        } catch (Exception e) {
            logger.error("Error during un-sign-up for memberEmail: {} and eventId: {}: {}", memberEmail, eventId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the un-sign up request.");
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

