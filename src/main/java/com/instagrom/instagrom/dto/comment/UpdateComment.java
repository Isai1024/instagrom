package com.instagrom.instagrom.dto.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateComment {
    
    private String text;

    public UpdateComment() {
    }

    public UpdateComment(String text) {
        this.text = text;
    }

}
