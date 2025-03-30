package com.trinity.planit.repository;

import com.trinity.planit.model.Member;
import com.trinity.planit.model.Organisation;
import com.trinity.planit.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrganisationRepository extends MongoRepository<Organisation, String> {


    void deleteByUsername(String username);

    Organisation findByUsername(String username);

    List<Organisation> findByName(String name);

    Organisation findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
