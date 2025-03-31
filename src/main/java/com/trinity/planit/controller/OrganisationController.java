package com.trinity.planit.controller;

import com.trinity.planit.model.Event;
import com.trinity.planit.model.Member;
import com.trinity.planit.model.Organisation;
import com.trinity.planit.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/organisations")
public class OrganisationController {
    @Autowired
    private OrganisationService organisationService;

    @GetMapping
    public List<Organisation> getAllOrganisations() {
        return organisationService.getAllOrganisations();
    }

    @GetMapping("/name/{name}")
    public List<Organisation> getOrganisationsByName(@PathVariable String name) {
        return organisationService.getOrganisationsByName(name);
    }

    @GetMapping("/{username}")
    public Organisation getOrganisationByUsername(@PathVariable String username) {
        return organisationService.getOrganisationByUsername(username);
    }


    @PostMapping
    public Organisation addOrganisation(@RequestBody Organisation organisation) {
        return organisationService.addOrganisation(organisation);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Organisation> updateOrganisation(@PathVariable String username, @RequestBody Organisation updatedOrganisation) {
        Organisation organisation = organisationService.updateOrganisationByUsername(username, updatedOrganisation);
        if (organisation != null) {
            return ResponseEntity.ok(organisation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{username}/events")
    public ResponseEntity<Organisation> addEventToOrganisation(@PathVariable String username, @RequestBody Event newEvent) {
        // Find the organisation by username
        Organisation organisation = organisationService.findByUsername(username);

        if (organisation != null) {
            // Ensure the 'creator' field is set with the organisation's name
            newEvent.setCreator(organisation.getName()); // Set the creator to the organisation's name

            // Set other fields in the event
            // For example, set the description, location, and date from the request body
            if (newEvent.getName() == null || newEvent.getDescription() == null || newEvent.getLocation() == null || newEvent.getDate() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Ensure all fields are present
            }

            // Add the fully populated event to the organisation's eventsCreated list
            if (organisation.getEventsCreated() == null) {
                organisation.setEventsCreated(new ArrayList<>());
            }
            organisation.getEventsCreated().add(newEvent);  // Add event to the list

            // Save the updated organisation with the new event added
            Organisation updatedOrganisation = organisationService.updateOrganisation(username, organisation);

            if (updatedOrganisation != null) {
                return ResponseEntity.ok(updatedOrganisation);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}
