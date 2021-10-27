package com.example.cinemabasegradle.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseModel {

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "active")
    private Boolean active = true;

    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Profile profile;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UserMovie> userMovieList;


}
