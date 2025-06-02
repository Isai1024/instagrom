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
@Table(name = "relationships")
public class RelationShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", unique = false, referencedColumnName = "id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id", unique = false, referencedColumnName = "id")
    private User following;

    @ManyToOne
    @JoinColumn(name = "status_id", unique = false, referencedColumnName = "id")
    private RelationStatus status;
    
    @Column
    private Date createdAt;
    
    @Column
    private Date updatedAt;

    public RelationShip() {
    }

    public RelationShip(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
    
}
