package com.sep6.backend.dataAccess.interfaces;

import com.sep6.backend.model.SearchResponse;

import java.util.List;
import java.util.Map;

public interface MoviesDataProvider {
    List<SearchResponse> search(String searchFilter);
}
