package com.instagrom.instagrom.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePost {
 
    private String caption;

    public UpdatePost(){
    }

    public UpdatePost(String caption) {
        this.caption = caption;
    }

}
