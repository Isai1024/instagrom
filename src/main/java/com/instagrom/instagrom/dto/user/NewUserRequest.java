package com.instagrom.instagrom.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewUserRequest {
    
    private String name;
    private String email;
    private String username;
    private String password;

    public NewUserRequest() {
    }

    public NewUserRequest(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
}
