package com.example.cinemabasegradle.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "profile")
public class Profile extends BaseModel {

    @Column(name = "avatar")
    private String avatar;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "age")
    private Integer age;
    @Column(name = "language")
    private String language;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
