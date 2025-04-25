package com.instagrom.instagrom.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comment;

    @Column
    private int commentsCount;

    @Column
    private int likesCount;

    @ManyToOne
    @JoinColumn(name = "user_id", unique = false, referencedColumnName = "id")
    private User user;
    
    @Column
    private Date createdAt;
    
    @Column
    private Date updatedAt;

    @Column
    private Date deletedAt;

    public Post() {
    }

    public Post(String imageName, String imageExtension, String caption, User user) {
        this.imageName = imageName;
        this.imageExtension = imageExtension;
        this.caption = caption;
        this.user = user;
    }

}
