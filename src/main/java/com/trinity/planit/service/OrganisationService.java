package com.trinity.planit.service;

import com.trinity.planit.model.Organisation;
import com.trinity.planit.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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


}