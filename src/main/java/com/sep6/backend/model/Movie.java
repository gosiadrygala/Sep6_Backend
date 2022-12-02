package com.sep6.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value= "Year")
    private Integer year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
}
