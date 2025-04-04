package com.trinity.planit.service;

import com.trinity.planit.model.Event;
import com.trinity.planit.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event getEventById(String eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    // Retrieve an event by its name
    public Event getEventByName(String name) {
        Optional<Event> eventOptional = eventRepository.findByName(name);
        return eventOptional.orElseThrow(() -> new NoSuchElementException("Event not found with name: " + name));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();  // Retrieves all events from the database
    }

    // Update event by its ID
    public Event updateEvent(String eventId, Event updatedEvent) {
        // Find the event by ID
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event not found with ID: " + eventId));

        // Update the fields of the existing event
        existingEvent.setName(updatedEvent.getName());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setLocation(updatedEvent.getLocation());
        existingEvent.setDate(updatedEvent.getDate());

        // Save the updated event back to the repository
        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(String eventId) {
        Event existingEvent = eventRepository.findById(eventId).orElseThrow(() ->
                new NoSuchElementException("Event not found with ID: " + eventId)
        );
        eventRepository.delete(existingEvent);  // Deletes the event from the repository
    }

    public List<Event> getEventsByEmail(String email) {
        // Assuming the Event entity has a 'creator' field that stores the creator's email
        return eventRepository.findByCreatorEmail(email);
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

}