package com.trinity.planit.model;

public class GoogleAuthRequest {

    private String idToken;
    private String userType;

    // Getters and setters

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
