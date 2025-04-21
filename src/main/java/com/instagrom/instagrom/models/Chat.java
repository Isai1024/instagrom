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
@Table(name = "chats")
public class Chat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", unique = false, referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", unique = false, referencedColumnName = "id")
    private User receiver;

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
    
    public Chat(User sender, User receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

}
