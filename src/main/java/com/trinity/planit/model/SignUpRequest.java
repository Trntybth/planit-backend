package com.trinity.planit.model;

public class SignUpRequest {
    private boolean signup;

    // Constructor
    public SignUpRequest() {}

    public SignUpRequest(boolean signup) {
        this.signup = signup;
    }

    // Getter
    public boolean isSignup() {
        return signup;
    }

    // Setter
    public void setSignup(boolean signup) {
        this.signup = signup;
    }
}
