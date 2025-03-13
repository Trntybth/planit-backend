package com.trinity.planit.service;

import com.trinity.planit.model.Organisation;
import com.trinity.planit.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrganisationService {
    @Autowired
    private OrganisationRepository organisationRepository;

    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    public List<Organisation> getOrganisationsByName(String name) {
        return organisationRepository.findByName(name);
    }

    public Organisation getOrganisationByUsername(String username) {
        return organisationRepository.findByUsername(username);
    }

    public Organisation addOrganisation(Organisation organisation) {
        return organisationRepository.save(organisation);
    }


    public Organisation updateOrganisationByUsername(String username, Organisation updatedOrganisation) {
        Organisation existingOrganisation = organisationRepository.findByUsername(username);

        if (existingOrganisation == null) {
            throw new NoSuchElementException("Organisation not found with username: " + username);
        }

        boolean updated = false;

        // Update name if provided
        if (updatedOrganisation.getName() != null && !updatedOrganisation.getName().isBlank()) {
            existingOrganisation.setName(updatedOrganisation.getName());
            updated = true;
        }


        // Update contactEmail if provided
        if (updatedOrganisation.getContactEmail() != null && !updatedOrganisation.getContactEmail().isBlank()) {
            existingOrganisation.setContactEmail(updatedOrganisation.getContactEmail());
            updated = true;
        }

        // Save and return updated organisation if any update occurred
        if (updated) {
            return organisationRepository.save(existingOrganisation);
        } else {
            throw new IllegalArgumentException("No valid fields provided for update.");
        }
    }




}