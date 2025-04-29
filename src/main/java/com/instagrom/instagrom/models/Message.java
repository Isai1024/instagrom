package com.instagrom.instagrom.models;

import java.util.Date;

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
@Table(name = "messages")
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", unique = false, referencedColumnName = "id")
    private User user;

    @Column
    private String message;

    @Column
    private boolean isRead;

    @Column
    private boolean isVisible;

    @Column
    private long imagePostId;
    
    @Column
    private Date createdAt;
    
    @Column
    private Date updatedAt;

    public Message() {
    }
    
    public Message(User user, String message, boolean isRead, boolean isVisible, long imagePostId) {
        this.user = user;
        this.message = message;
        this.isRead = isRead;
        this.isVisible = isVisible;
        this.imagePostId = imagePostId;
    }

}
