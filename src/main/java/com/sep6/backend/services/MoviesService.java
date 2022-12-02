package com.sep6.backend.services;

import com.sep6.backend.dataAccess.interfaces.MoviesDataProvider;
import com.sep6.backend.model.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MoviesService {

    private final MoviesDataProvider moviesDataProvider;

    @Autowired
    public MoviesService(MoviesDataProvider moviesDataProvider){
        this.moviesDataProvider = moviesDataProvider;
    }

    public List<SearchResponse> search(String searchFilter) {
        return moviesDataProvider.search(searchFilter);
    }
}