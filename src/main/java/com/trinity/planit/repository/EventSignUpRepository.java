package com.trinity.planit.repository;

import com.trinity.planit.model.EventSignUp;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;
import java.util.Optional;


public interface EventSignUpRepository extends MongoRepository<EventSignUp, ObjectId> {

    List<EventSignUp> findByEventId(ObjectId eventId);

    // Find by memberEmail and eventId (use ObjectId for eventId)
    Optional<EventSignUp> findByMemberEmailAndEventId(String memberEmail, ObjectId eventId);

    // Find all sign-ups for a member with signup status as true
    List<EventSignUp> findByMemberEmailAndSignupTrue(String memberEmail);

    boolean existsByMemberEmailAndEventIdAndSignupTrue(String memberEmail, String eventId);

    Optional<EventSignUp> findByMemberEmailAndEventIdAndSignupTrue(String memberEmail, ObjectId eventId);



}
