package com.instagrom.instagrom.dto.comment;

import java.util.Date;

import com.instagrom.instagrom.dto.user.UserResponse;
import com.instagrom.instagrom.models.Comment;
import com.instagrom.instagrom.models.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentResponse {

    private long id;
    private String text;
    private User user;
    private Date createdAt;

    public CommentResponse() {}

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.user = comment.getUser();
        this.createdAt = comment.getCreatedAt();
    }

    public UserResponse getUser() {
        return new UserResponse(user);
    }
    
}
