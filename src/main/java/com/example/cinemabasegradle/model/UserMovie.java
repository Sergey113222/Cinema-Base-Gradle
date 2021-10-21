package com.example.cinemabasegradle.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
