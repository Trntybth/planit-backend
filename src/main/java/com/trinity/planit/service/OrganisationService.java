package com.trinity.planit.service;

import com.trinity.planit.model.Event;
import com.trinity.planit.model.Organisation;
import com.trinity.planit.repository.EventRepository;
import com.trinity.planit.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrganisationService {
    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private EventRepository eventRepository;

    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    public List<Organisation> getOrganisationsByName(String name) {
        return organisationRepository.findByName(name);
    }

    public Organisation addOrganisation(Organisation organisation) {
        return organisationRepository.save(organisation);
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

            // Save the updated organisation with the new details
            return organisationRepository.save(existingOrganisation);
        }

        // If organisation is not found, return null
        return null;
    }



    public void saveOrganisation(Organisation organisation) {
        organisationRepository.save(organisation);
    }

    public Organisation getOrganisationByEmail(String email) {
        return organisationRepository.findByEmail(email).orElse(null);
    }


}
