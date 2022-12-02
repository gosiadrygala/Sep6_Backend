package com.sep6.backend.controllers;

import com.sep6.backend.model.Movie;
import com.sep6.backend.model.SearchResponse;
import com.sep6.backend.services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/movies")
public class MoviesController {

    private MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @PostMapping("/search")
    public List<SearchResponse> search(@RequestBody String searchFilter){
        return moviesService.search(searchFilter);
    }

    @PostMapping("/moviedata")
    public Movie getExtendedMovieData(@RequestParam String id){
        return moviesService.getExtendedMovieData(id);
    }


}