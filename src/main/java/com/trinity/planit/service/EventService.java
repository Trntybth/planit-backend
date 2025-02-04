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

    // Retrieve an event by its name
    public Event getEventByName(String name) {
        Optional<Event> eventOptional = eventRepository.findByName(name);
        return eventOptional.orElseThrow(() -> new NoSuchElementException("Event not found with name: " + name));
    }
    public List<Event> getAllEvents() {
        return eventRepository.findAll();  // Retrieves all events from the database
    }

    public Event createEvent(Event newEvent) {
        // Save the new event to the repository (MongoDB)
        return eventRepository.save(newEvent);
    }
}
