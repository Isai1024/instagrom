package com.instagrom.instagrom.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "likes_post")
public class LikesPost {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "post_id", unique = false, referencedColumnName = "id")
    private Post post;
    
    @ManyToOne
    @JoinColumn(name = "user_id", unique = false, referencedColumnName = "id")
    private User user;

    @Column
    private boolean isLiked;

}
