package com.sep6.backend.dataAccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sep6.backend.dataAccess.interfaces.ExtendedMovieDataProvider;
import com.sep6.backend.exception.RetrieveDataException;
import com.sep6.backend.model.Movie;
import com.sep6.backend.model.MovieShort;
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
        String initialId = id;
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
            deserialized.setId(initialId);
            if(deserialized.getPoster() == null || deserialized.getPoster().equals("N/A")) {
                deserialized.setPoster("https://motivatevalmorgan.com/wp-content/uploads/2016/06/default-movie.jpg");
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RetrieveDataException("Could not retrieve extended movie data.");
        }
        return deserialized;
    }

    @Override
    public MovieShort getShortenedMovieData(String id) {
        String initialId = id;
        StringBuilder idBuilder = new StringBuilder(id);
        while(idBuilder.length() < 7){
            idBuilder.insert(0, "0");
        }
        id = idBuilder.toString();

        String uri = "http://www.omdbapi.com/?i=tt"+id+"&apikey="+apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        Movie deserialized = null;
        MovieShort movie = new MovieShort();
        try {
            deserialized = MovieDeserializer.deserialize(result);
            movie.setId(initialId);
            movie.setTitle(deserialized.getTitle());
            if(deserialized.getPoster() == null || deserialized.getPoster().equals("N/A")) {
                movie.setPoster("https://motivatevalmorgan.com/wp-content/uploads/2016/06/default-movie.jpg");
            }
            else{
                movie.setPoster(deserialized.getPoster());
            }

        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RetrieveDataException("Could not retrieve shortened movie data.");
        }
        return movie;
    }
}
