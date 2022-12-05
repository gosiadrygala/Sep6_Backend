package com.sep6.backend.services;

import com.sep6.backend.dataAccess.interfaces.ExtendedMovieDataProvider;
import com.sep6.backend.dataAccess.interfaces.MoviesDataProvider;
import com.sep6.backend.model.Movie;
import com.sep6.backend.model.MovieShort;
import com.sep6.backend.model.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoviesService {

    private final MoviesDataProvider moviesDataProvider;
    private final ExtendedMovieDataProvider extendedMovieDataProvider;

    @Autowired
    public MoviesService(MoviesDataProvider moviesDataProvider, ExtendedMovieDataProvider extendedMovieDataProvider){
        this.moviesDataProvider = moviesDataProvider;
        this.extendedMovieDataProvider = extendedMovieDataProvider;
    }

    public List<SearchResponse> search(String searchFilter) {
        return moviesDataProvider.search(searchFilter);
    }

    public Movie getExtendedMovieData(String id) {
        return extendedMovieDataProvider.getExtendedMovieData(id);
    }

    public List<MovieShort> getShortenedMovieData() {
        List<Integer> ids = moviesDataProvider.getTwentyRandomIDS();

        List<MovieShort> movies = new ArrayList<>();
        for (Integer id: ids) {
            MovieShort shortenedMovieData = extendedMovieDataProvider.getShortenedMovieData(String.valueOf(id));
            if(!shortenedMovieData.getPoster().equals("N/A") && shortenedMovieData.getPoster() != null)
                movies.add(shortenedMovieData);
        }
        return movies;
    }
}