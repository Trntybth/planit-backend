package com.trinity.planit.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "event_signups")
public class EventSignUp {

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ObjectId id;  // Use String here for ID
    private String memberEmail;  // Member email who is signing up


    @Field("eventId")
    private ObjectId eventId;  // Change this to String
    private boolean signup;  // true for signed up, false for not signed up

    // Getters and setters

    public ObjectId getEventId() {
        return eventId;
    }

    public void setEventId(ObjectId eventId) {
        this.eventId = eventId;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }



    public boolean isSignup() {
        return signup;
    }

    public void setSignup(boolean signup) {
        this.signup = signup;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
