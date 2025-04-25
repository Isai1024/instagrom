package com.instagrom.instagrom.dto.user;

public class AuthRequest {
    private String username;
    private String password;

    // Constructors, Getters, Setters, etc.
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
