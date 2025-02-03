package com.trinity.planit.repository;

import com.trinity.planit.model.Member;
import com.trinity.planit.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrganisationRepository extends MongoRepository<Member, String> {

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}
