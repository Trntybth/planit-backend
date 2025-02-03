package com.trinity.planit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "members")
@JsonInclude(JsonInclude.Include.NON_NULL) // Pre
public class Member extends User {

    @JsonProperty("eventsList")
    private List<Event> eventsList;

}
