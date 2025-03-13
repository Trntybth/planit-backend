package com.trinity.planit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "members")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member extends User {

    // Google auth constructor to create Member object using Google Sign-In info
    public Member(String email, String name, String picture) {
        // Populate fields from Google Sign-In information
        this.setContactEmail(email);
        this.setName(name);
        this.setUsername(generateUsername(email)); // You can create a default username based on email or some logic
    }

    public List<Event> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Event> eventsList) {
        this.eventsList = eventsList;
    }

    @JsonProperty("eventsList")
    private List<Event> eventsList;

    // generate a random username
    private String generateUsername(String email) {
        return email != null ? email.split("@")[0] : "guest";
    }
}
