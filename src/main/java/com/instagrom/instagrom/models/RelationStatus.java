package com.instagrom.instagrom.models;

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
@Table(name = "relation_status")
public class RelationStatus { // * CATALOG OF RELATION STATUS * //
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String status;

    public RelationStatus() {
    }

    public RelationStatus(long id, String status) {
        this.id = id;
        this.status = status;
    }

}
