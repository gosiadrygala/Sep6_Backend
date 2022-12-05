package com.sep6.backend.dataAccess.interfaces;

import com.sep6.backend.model.SearchResponse;

import java.util.List;

public interface MoviesDataProvider {
    List<SearchResponse> search(String searchFilter);
    List<Integer> getTwentyRandomIDS();
}
