package com.instagrom.instagrom.dto.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewPost {

    private String caption;

    private String imageName;

    private String imageExtension;

    public NewPost() {
    }

    public NewPost(String caption, String imageName, String imageExtension) {
        this.caption = caption;
        this.imageName = imageName;
        this.imageExtension = imageExtension;
    }
    
}
