package com.instagrom.instagrom.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String imageName;

    @Column
    private String imageExtension;

    @Column
    private String caption;

    @Column
    private long commentId;

    @Column
    private int commentsCount;

    @Column
    private int likesCount;

    @Column
    private long userId;
    
    @Column
    private Date createdAt;
    
    @Column
    private Date updatedAt;

    public Post() {
    }

    public Post(String imageName, String imageExtension, String caption, long userId) {
        this.imageName = imageName;
        this.imageExtension = imageExtension;
        this.caption = caption;
        this.userId = userId;
    }

}
