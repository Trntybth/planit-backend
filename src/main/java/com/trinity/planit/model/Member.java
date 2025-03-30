package com.trinity.planit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "members")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member extends User {

    // Google auth constructor to create Member object using Google Sign-In info
    public Member(String username, String email, String name) {
        // Populate fields from Google Sign-In information
        this.setUsername(username);  // Set the username from user input in the Android app
        this.setEmail(email); // Set the email from Google
        this.setName(name);           // Set the name from Google
        this.setUserType("Member");   // Set user type to "Member"

        // Initialize the eventsList as an empty list
        this.eventsList = new ArrayList<>();  // Initialize as ArrayList<Event>
    }
    @JsonProperty("eventsList")
    private List<Event> eventsList;

    public List<Event> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Event> eventsList) {
        this.eventsList = eventsList;
    }


}
