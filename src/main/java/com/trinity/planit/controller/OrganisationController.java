package com.trinity.planit.controller;

import com.trinity.planit.model.Event;
import com.trinity.planit.model.Organisation;
import com.trinity.planit.service.EventService;
import com.trinity.planit.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/organisations")
public class OrganisationController {
    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Organisation> getAllOrganisations() {
        return organisationService.getAllOrganisations();
    }

    @GetMapping("/name/{name}")
    public List<Organisation> getOrganisationsByName(@PathVariable String name) {
        return organisationService.getOrganisationsByName(name);
    }


    @PostMapping
    public Organisation addOrganisation(@RequestBody Organisation organisation) {
        return organisationService.addOrganisation(organisation);
    }





}
