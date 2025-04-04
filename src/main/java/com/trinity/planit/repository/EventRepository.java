package com.trinity.planit.repository;

import com.trinity.planit.model.Event;
import com.trinity.planit.model.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {

    Optional<Event> findByName(String name);

    List<Event> findByCreatorEmail(String email);

}