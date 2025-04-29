package com.instagrom.instagrom.dto.comment;

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

    public CommentResponse() {}

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.user = comment.getUser();
    }
    
}
