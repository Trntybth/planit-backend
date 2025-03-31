package com.trinity.planit.service;

import com.trinity.planit.model.Organisation;
import com.trinity.planit.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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



    public Organisation addOrganisation(Organisation organisation) {
        return organisationRepository.save(organisation);
    }

    public Organisation getOrganisationByUsername(String username) {
        return organisationRepository.findByUsername(username);
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
        if (updatedOrganisation.getEmail() != null && !updatedOrganisation.getEmail().isBlank()) {
            existingOrganisation.setEmail(updatedOrganisation.getEmail());
            updated = true;
        }


        // Save and return updated organisation if any update occurred
        if (updated) {
            return organisationRepository.save(existingOrganisation);
        } else {
            throw new IllegalArgumentException("No valid fields provided for update.");
        }
    }


    public Organisation getOrganisationByEmail(String email) {
        return organisationRepository.findByEmail(email).orElse(null);
    }

    public Organisation updateOrganisation(String username, Organisation updatedOrganisation) {
        // Find the existing organisation by username
        Organisation existingOrganisation = organisationRepository.findByUsername(username);

        // Check if the organisation exists
        if (existingOrganisation != null) {
            // Update fields from the updatedOrganisation object
            existingOrganisation.setName(updatedOrganisation.getName());
            existingOrganisation.setEmail(updatedOrganisation.getEmail());
            existingOrganisation.setUserType(updatedOrganisation.getUserType());

            // If eventsCreated has been updated, set it
            if (updatedOrganisation.getEventsCreated() != null) {
                existingOrganisation.setEventsCreated(updatedOrganisation.getEventsCreated());
            }

            // Save the updated organisation with the new details and eventsCreated list
            return organisationRepository.save(existingOrganisation);
        }

        // If organisation is not found, return null
        return null;
    }

    public Organisation findByUsername(String username) {
        return organisationRepository.findByUsername(username);
    }
}