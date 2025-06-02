package com.instagrom.instagrom.dto.post;

import com.instagrom.instagrom.dto.user.UserResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikesResponse {
    
    private long id;
    private UserResponse user;
    private boolean isLiked;

    public LikesResponse() {}

    public LikesResponse(long id, UserResponse user, boolean isLiked) {
        this.id = id;
        this.user = user;
        this.isLiked = isLiked;
    }

}
