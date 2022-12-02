package com.sep6.backend.dataAccess;

import com.sep6.backend.dataAccess.interfaces.MoviesDataProvider;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SQLMoviesDataProvider implements MoviesDataProvider {

    private final String url;
    private final String username;
    private final String password;

    public SQLMoviesDataProvider(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @SneakyThrows
    public Map<String,String> search(String searchFilter) {
        Connection connection = null;
        Map<String, String> searchResult = new HashMap<>();
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement readStatement = connection.prepareStatement("SELECT TOP 10 id, title FROM dbo.movies WHERE title LIKE '%" + searchFilter +"%';");

            ResultSet resultSet = readStatement.executeQuery();

            while(resultSet.next()){
                searchResult.put(String.valueOf(resultSet.getInt("id")), resultSet.getString("title"));
            }

            return searchResult;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return searchResult;
    }
}
