package com.sep6.backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sep6.backend.model.Movie;


public class MovieDeserializer {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static Movie deserialize(String data) throws JsonProcessingException {
        return objectMapper.readValue(data, Movie.class);
    }
}
