package com.sep6.backend.dataAccess.interfaces;

import com.sep6.backend.model.DataItem;
import com.sep6.backend.model.SearchResponse;

import java.util.List;

public interface MoviesDataProvider {
    List<SearchResponse> search(String searchFilter);
    List<Integer> getTwentyRandomIDS();
    boolean favouriteMovie(String email, int movieID);

    boolean isFavouriteMovie(String email, int movieID);

    List<Integer> getIDSFavouriteMovies(String email);

    List<DataItem> getRatingOverYears();
}
