package com.example.cinemabasegradle.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created")
    protected LocalDate created;

    @Column(name = "updated")
    protected LocalDate updated;

    @PrePersist
    protected void onCreate() {
        created = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = LocalDate.now();
    }
}
