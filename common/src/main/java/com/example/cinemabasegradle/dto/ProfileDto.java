package com.example.cinemabasegradle.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class ProfileDto {
    private Long id;
    private String avatar;
    private String firstName;
    private String lastName;
    private Integer age;
    private String language;
    private UserDto userDto;
}
