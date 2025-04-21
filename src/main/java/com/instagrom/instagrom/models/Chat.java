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
@Table(name = "chats")
public class Chat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long senderId;

    @Column
    private long receiverId;

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

    public Chat() {
    }
    
    public Chat(long senderId, long receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

}
