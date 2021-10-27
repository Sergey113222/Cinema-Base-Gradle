package com.example.cinemabasegradle.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_movie")
public class UserMovie extends BaseModel {

    @Column(name = "external_movie_id")
    private Long externalMovieId;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "notes")
    private String notes;
    @Column(name = "viewed")
    private boolean viewed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
