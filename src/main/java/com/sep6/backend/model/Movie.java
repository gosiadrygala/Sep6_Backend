package com.sep6.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value= "Year")
    private Integer year;
    @JsonProperty(value="Rated")
    private String rated;
    @JsonProperty(value="Released")
    private String released;
    @JsonProperty(value="Runtime")
    private String runtime;
    @JsonProperty(value="Genre")
    private String genre;
    @JsonProperty(value="Director")
    private String director;
    @JsonProperty(value="Writer")
    private String writer;
    @JsonProperty(value="Actors")
    private String actors;
    @JsonProperty(value="Plot")
    private String plot;
    @JsonProperty(value="Language")
    private String language;
    @JsonProperty(value="Country")
    private String country;
    @JsonProperty(value="Awards")
    private String awards;
    @JsonProperty(value="Poster")
    private String poster;
    @JsonProperty(value="imdbRating")
    private Float rating;
    @JsonProperty(value="imdbVotes")
    private String votes;
    @JsonProperty(value="imdbID")
    private String id;
    @JsonProperty(value="BoxOffice")
    private String boxOffice;
}
