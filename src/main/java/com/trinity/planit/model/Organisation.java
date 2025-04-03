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
@JsonInclude(JsonInclude.Include.NON_NULL)

    public class Organisation extends User {

        // Google auth constructor to create Organisation object using Google Sign-In info
        public Organisation(String username, String email, String name) {
            // Populate fields from Google Sign-In information
            this.setUsername(username);      // Set the username from user input in the Android app
            this.setEmail(email);     // Set the email from Google
            this.setName(name);              // Set the name from Google
            this.setUserType("Organisation");  // Set user type to "Organisation"

        }


}
