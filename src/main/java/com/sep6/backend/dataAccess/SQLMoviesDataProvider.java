package com.sep6.backend.dataAccess;

import com.sep6.backend.dataAccess.interfaces.MoviesDataProvider;
import com.sep6.backend.model.SearchResponse;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<SearchResponse> search(String searchFilter) {
        Connection connection = null;
        List<SearchResponse> searchResult = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement readStatement = connection.prepareStatement("SELECT TOP 30 id, title FROM dbo.movies WHERE title LIKE '%" + searchFilter +"%';");

            ResultSet resultSet = readStatement.executeQuery();

            while(resultSet.next()){
                searchResult.add(new SearchResponse(resultSet.getInt("id"), resultSet.getString("title")));
            }

            return searchResult;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return searchResult;
    }

    @SneakyThrows
    @Override
    public List<Integer> getTwentyRandomIDS() {
        Connection connection = null;
        List<Integer> searchResult = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement readStatement = connection.prepareStatement("SELECT TOP 20 id FROM dbo.movies ORDER BY NEWID() ");

            ResultSet resultSet = readStatement.executeQuery();

            while(resultSet.next()){
                searchResult.add(resultSet.getInt("id"));
            }

            return searchResult;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return searchResult;
    }

    @SneakyThrows
    @Override
    public boolean favouriteMovie(String email, int movieID) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM dbo.favouriteMovies WHERE movieID='" +
                    movieID + "'AND email='" + email + "';");

            ResultSet resultSet = readStatement.executeQuery();
            if (!resultSet.next()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dbo.favouriteMovies (movieID, email)" +
                        "VALUES ('" + movieID + "', '" + email
                        + "');");
                preparedStatement.executeUpdate();
                return true;
            }
            else{
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM dbo.favouriteMovies WHERE " +
                        "movieID ='" + movieID + "' AND email = '" + email
                        + "';");
                preparedStatement.executeUpdate();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }

    @SneakyThrows
    @Override
    public boolean isFavouriteMovie(String email, int movieID) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM dbo.favouriteMovies WHERE movieID='" +
                    movieID + "'AND email='" + email + "';");

            ResultSet resultSet = readStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
            else{
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }

    @SneakyThrows
    @Override
    public List<Integer> getIDSFavouriteMovies(String email) {
        Connection connection = null;
        List<Integer> searchResult = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement readStatement = connection.prepareStatement("SELECT movieID FROM dbo.favouriteMovies WHERE email='"+ email +"';");

            ResultSet resultSet = readStatement.executeQuery();

            while(resultSet.next()){
                searchResult.add(resultSet.getInt("movieID"));
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
