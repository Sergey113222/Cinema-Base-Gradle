package com.example.cinemabasegradle.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SearchDto {
    @NotBlank
    @Size(min = 1, max = 64, message = "Query should be between [1-64]")
    private String query;
    private String lang = "en-US";
    private boolean includeAdult;
}
