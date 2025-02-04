package com.trinity.planit.controller;

import com.trinity.planit.model.Organisation;
import com.trinity.planit.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
