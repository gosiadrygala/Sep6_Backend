package com.sep6.backend.dataAccess.interfaces;

import java.util.Map;

public interface MoviesDataProvider {
    Map<String, String> search(String searchFilter);
}
