package com.sep6.backend.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sep6.backend.model.Movie;
import com.sep6.backend.model.MovieShort;
import com.sep6.backend.model.SearchResponse;
import com.sep6.backend.services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
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

    @GetMapping("/moviedata")
    public Movie getExtendedMovieData(@RequestParam String id){
        return moviesService.getExtendedMovieData(id);
    }

    @GetMapping("/getRandomMovies")
    public List<MovieShort> getShortenedMovieData(){
        return moviesService.getShortenedMovieData();
    }

    @PostMapping(value = "/favouriteMovie")
    public boolean favouriteMovie(@RequestBody ObjectNode objectNode){
        String email = objectNode.get("email").asText();
        String movieID = objectNode.get("movieID").asText();
        return moviesService.favouriteMovie(email, Integer.parseInt(movieID));
    }

    @PostMapping("/isFavouriteMovie")
    public boolean isFavouriteMovie(@RequestBody ObjectNode objectNode){
        String email = objectNode.get("email").asText();
        String movieID = objectNode.get("movieID").asText();
        return moviesService.isFavouriteMovie(email, Integer.parseInt(movieID));
    }

    @GetMapping("getFavouriteMovies")
    public List<MovieShort> getFavouriteMovies(@RequestParam String email){
        return moviesService.getFavouriteMovies(email);
    }
}