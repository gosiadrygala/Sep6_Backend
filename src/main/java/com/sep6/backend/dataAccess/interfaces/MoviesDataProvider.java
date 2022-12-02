package com.sep6.backend.dataAccess.interfaces;

import java.util.Map;

public interface MoviesDataProvider {
    Map<Integer, String> search(String searchFilter);
}
