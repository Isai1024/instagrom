package com.instagrom.instagrom.dto.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewComment {
    
    private String text;
    private long postId;

    public NewComment() {
    }

    public NewComment(String text, long postId) {
        this.text = text;
        this.postId = postId;
    }

}
