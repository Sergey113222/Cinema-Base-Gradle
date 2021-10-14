package com.example.cinemabasegradle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private String avatar;
    private String about;
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    private String region;
    private String language;
}
