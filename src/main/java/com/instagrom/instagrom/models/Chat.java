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
@Table(name = "chats")
public class Chat { // * Relations between users and messages

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String chatUUID;
    
    @ManyToOne
    @JoinColumn(name = "message_id", unique = false, referencedColumnName = "id")
    private Message message; // message in the chat

    @ManyToOne
    @JoinColumn(name = "sender_id", unique = false, referencedColumnName = "id")
    private User sender; // user who sent the message

    @ManyToOne
    @JoinColumn(name = "receiver_id", unique = false, referencedColumnName = "id")
    private User receiver; // user who received the message

    public Chat() {
    }

    
    
}
