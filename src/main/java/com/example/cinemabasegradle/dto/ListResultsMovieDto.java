package com.example.cinemabasegradle.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonAutoDetect
public class ListResultsMovieDto {
    private List<MovieDto> results;
    private Long page;
    @JsonProperty("total_pages")
    private Long totalPages;
    @JsonProperty("total_results")
    private Long totalResults;

    public ListResultsMovieDto() {
        results = new ArrayList<>();
    }
}
