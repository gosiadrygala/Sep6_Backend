package com.sep6.backend.dataAccess.interfaces;

import com.sep6.backend.model.Movie;

public interface ExtendedMovieDataProvider {
    Movie getExtendedMovieData(String id);
}
