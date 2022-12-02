package com.sep6.backend.controllers;

import com.sep6.backend.model.SearchResponse;
import com.sep6.backend.services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="api/v1/movies")
public class MoviesController {

    private MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping("/search")
    public List<SearchResponse> search(@RequestBody String searchFilter){
        return moviesService.search(searchFilter);
    }


}