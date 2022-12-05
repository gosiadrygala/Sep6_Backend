package com.sep6.backend.dataAccess.interfaces;

import com.sep6.backend.model.Movie;
import com.sep6.backend.model.MovieShort;

public interface ExtendedMovieDataProvider {
    Movie getExtendedMovieData(String id);
    MovieShort getShortenedMovieData(String valueOf);
}
