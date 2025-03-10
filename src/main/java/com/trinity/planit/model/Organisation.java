package com.trinity.planit.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "organisations")
@JsonInclude(JsonInclude.Include.NON_NULL) // Pre
public class Organisation extends User {

    public Organisation(String email, String name, String picture) {
    }

    @JsonProperty("eventsCreated")
    private List<Event> eventsCreated;

}
