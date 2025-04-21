package com.instagrom.instagrom.models;

import java.sql.Date;

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
@Table(name = "comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String text;

    @Column
    private long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", unique = false, referencedColumnName = "id")
    private User user;
    
    @Column
    private Date createdAt;
    
    @Column
    private Date updatedAt;

    public Comment() {
    }

    public Comment(String text, long postId, User user) {
        this.text = text;
        this.postId = postId;
        this.user = user;
    }

}
