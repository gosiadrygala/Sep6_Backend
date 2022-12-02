package com.sep6.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
