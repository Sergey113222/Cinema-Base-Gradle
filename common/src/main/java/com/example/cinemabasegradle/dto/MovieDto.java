package com.example.cinemabasegradle.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@JsonAutoDetect
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class MovieDto {
    @NotNull(message = "Name cannot be null")
    @JsonProperty("id")
    private Long externalMovieId;
    private String title;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("release_date")
    private Date releaseDate;
    @JsonProperty("vote_average")
    private Double voteAverage;
    private String overview;
    private Boolean adult;
    @JsonProperty("genres")
    private List<ExternalGenreDto> genreDtoList;
    @JsonProperty("personal_rating")
    @Min(value = 0, message = "Rating should be between [0-10]")
    @Max(value = 10, message = "Rating should be between [0-10]")
    private Integer personalRating;
    @Pattern(regexp = ".{2,128}", message = "Notes should be between [2-128]")
    @JsonProperty("personal_notes")
    private String personalNotes;
    private LocalDate created;
}

