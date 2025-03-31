package com.trinity.planit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
@JsonInclude(JsonInclude.Include.NON_NULL) // Prevents empty JSON responses
public abstract class User {

    @Id
    private String id;


    @Indexed(unique = true)
    @JsonProperty("username")
    private String username;

    @JsonProperty("name")
    private String name;


    @Indexed(unique = true)
    @JsonProperty("email")
    private String email;

    public String getUserType() {
        return userType;
    }

    @JsonProperty("userType")
    private String userType;

    public void setUserType(String userType) {
        this.userType = userType;
    }

    //getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
