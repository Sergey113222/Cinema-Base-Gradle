package com.example.cinemabasegradle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Long id;
    private String avatar;
    private String firstName;
    private String lastName;
    private Integer age;
    private String language;
    public UserDto userDto;
}
