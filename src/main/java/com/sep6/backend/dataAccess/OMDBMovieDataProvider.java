package com.sep6.backend.dataAccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sep6.backend.dataAccess.interfaces.ExtendedMovieDataProvider;
import com.sep6.backend.model.Movie;
import com.sep6.backend.utils.MovieDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class OMDBMovieDataProvider implements ExtendedMovieDataProvider {

    private String apiKey;

    public OMDBMovieDataProvider(String apiKey){
        this.apiKey = apiKey;
    }

    @Override
    public Movie getExtendedMovieData(String id) {
        StringBuilder idBuilder = new StringBuilder(id);
        while(idBuilder.length() < 7){
            idBuilder.insert(0, "0");
        }
        id = idBuilder.toString();

        String uri = "http://www.omdbapi.com/?i=tt"+id+"&apikey="+apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        Movie deserialized = null;
        try {
            deserialized = MovieDeserializer.deserialize(result);
        } catch (JsonProcessingException e) {
            log.debug(e.getMessage());
        }
        return deserialized;
    }
}
