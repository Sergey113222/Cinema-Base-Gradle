package com.example.cinemabasegradle.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.cinemabasegradle.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class UserDto {
    private Long id;
    @NotNull(message = "Name cannot be null")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
    private String password;
    private Role role;
    private ProfileDto profileDto;
}
