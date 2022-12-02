package com.sep6.backend.services;

import com.sep6.backend.dataAccess.interfaces.MoviesDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MoviesService {

    private final MoviesDataProvider moviesDataProvider;

    @Autowired
    public MoviesService(MoviesDataProvider moviesDataProvider){
        this.moviesDataProvider = moviesDataProvider;
    }

    public Map<String, String> search(String searchFilter) {
        return moviesDataProvider.search(searchFilter);
    }
}