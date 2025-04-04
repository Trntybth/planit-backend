package com.trinity.planit.service;

import com.trinity.planit.model.Event;
import com.trinity.planit.model.EventSignUp;
import com.trinity.planit.repository.EventSignUpRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventSignUpService {

    private static final Logger logger = LoggerFactory.getLogger(EventSignUpService.class);

    @Autowired
    private EventSignUpRepository eventSignUpRepository;

    public List<EventSignUp> getSignUpsByEventId(ObjectId eventId) {
        List<EventSignUp> signUps = eventSignUpRepository.findByEventId(eventId);

        if (signUps.isEmpty()) {
            logger.warn("No sign-ups found for eventId: {}", eventId);
        } else {
            logger.info("Retrieved {} sign-ups for eventId: {}", signUps.size(), eventId);
        }

        return signUps;
    }

    public boolean isAlreadySignedUp(String memberEmail, String eventId) {
        return eventSignUpRepository.existsByMemberEmailAndEventIdAndSignupTrue(memberEmail, eventId);
    }

}