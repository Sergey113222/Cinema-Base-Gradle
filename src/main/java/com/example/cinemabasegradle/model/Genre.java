package com.example.cinemabasegradle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genre")
public class Genre extends BaseModel {
    @Column(name = "external_id")
    private Long externalId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private List<Movie> movies = new ArrayList<>();
}
