package com.trinity.planit.controller;

import com.trinity.planit.model.Event;
import com.trinity.planit.model.Organisation;
import com.trinity.planit.service.EventService;
import com.trinity.planit.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/events")

public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private OrganisationService organisationService;

    // Endpoint to retrieve an event by its ID
    @GetMapping("/id/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable String eventId) {
        Event event = eventService.getEventById(eventId);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }


    // Endpoint to retrieve an event by its name
    @GetMapping("/{eventName}")
    public ResponseEntity<Event> getEventByName(@PathVariable String eventName) {
        Event event = eventService.getEventByName(eventName);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);  // Returns a list of all events
    }

    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        System.out.println("Creating event for: " + event.getName());  // Debugging log to check for multiple calls

        // Step 1: Save the event
        Event savedEvent = eventService.saveEvent(event); // Save event to the database

        // Step 2: Return the created event in the response with a status 201
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    // PUT method to update an existing event
    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable String eventId, @RequestBody Event updatedEvent) {
        try {
            // Update event with the given eventId
            Event event = eventService.updateEvent(eventId, updatedEvent);
            return ResponseEntity.ok(event);  // Returns the updated event
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found if event doesn't exist
        }
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
        try {
            eventService.deleteEvent(eventId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 No Content for successful deletion
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found if event doesn't exist
        }

        // get events by email
    }
    @GetMapping("/events/{email}")
    public List<Event> getEventsByEmail(@PathVariable String email) {
        return eventService.getEventsByEmail(email);  // Service method filters events by email
    }


}
