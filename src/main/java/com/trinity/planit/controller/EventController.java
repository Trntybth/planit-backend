package com.trinity.planit.controller;

import com.trinity.planit.model.Event;
import com.trinity.planit.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

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

    // POST method to create a new event
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event newEvent) {
        Event createdEvent = eventService.createEvent(newEvent); // Delegate to service layer
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }
}
