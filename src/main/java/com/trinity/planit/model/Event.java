package com.trinity.planit.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "events")
@JsonInclude(JsonInclude.Include.NON_NULL) // Pre
public class Event {
    @Id
    private ObjectId id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("location")

    private String location;

    @JsonProperty("creator")
    private Organisation creator;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("attendees")
    private List<Member> attendees;



    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Organisation getCreator() {
        return creator;
    }

    public void setCreator(Organisation creator) {
        this.creator = creator;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getAttendees() {
        return attendees;
    }


}
