package com.instagrom.instagrom.models;

import java.sql.Date;

import com.instagrom.instagrom.models.util.RelationStatus;

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
@Table(name = "relationships")
public class RelationShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long followerId;

    @Column
    private long followingId;

    @Column
    private RelationStatus status;
    
    @Column
    private Date createdAt;
    
    @Column
    private Date updatedAt;

    public RelationShip() {
    }

    public RelationShip(long followerId, long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }
    
}
