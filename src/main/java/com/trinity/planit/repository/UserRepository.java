package com.trinity.planit.repository;

import com.trinity.planit.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByEmail(String email);
}
