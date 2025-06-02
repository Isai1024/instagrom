package com.instagrom.instagrom.dto.user;

import com.instagrom.instagrom.models.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponse {
    
    private long id;
    private String username;

    public UserResponse() {}

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

}
