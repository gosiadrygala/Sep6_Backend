package com.sep6.backend.dataAccess;

import com.sep6.backend.dataAccess.interfaces.MoviesDataProvider;
import com.sep6.backend.exception.RetrieveDataException;
import com.sep6.backend.model.DataItem;
import com.sep6.backend.model.Person;
import com.sep6.backend.model.SearchResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
            log.error(e.getMessage());
            throw new RetrieveDataException("Could not retrieve search items.");
        } finally {
            connection.close();
        }
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
            log.error(e.getMessage());
            throw new RetrieveDataException("Could not retrieve random movie data.");
        } finally {
            connection.close();
        }
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
            log.error(e.getMessage());
            throw new RetrieveDataException("Could not add movie to favourite.");
        } finally {
            connection.close();
        }
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
            log.error(e.getMessage());
            throw new RetrieveDataException("Could not retrieve if the movie is in favourite.");
        } finally {
            connection.close();
        }
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
            log.error(e.getMessage());
            throw new RetrieveDataException("Could not retrieve favourite movies.");
        } finally {
            connection.close();
        }
    }

    @SneakyThrows
    @Override
    public List<DataItem> getRatingOverYears() {
        Connection connection = null;
        List<DataItem> searchResult = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement readStatement = connection.prepareStatement("SELECT year, AVG(rating) as avg_rating FROM dbo.movies \n" +
                    "INNER JOIN dbo.ratings\n" +
                    "on dbo.movies.id = dbo.ratings.movie_id\n" +
                    "group by year;");

            ResultSet resultSet = readStatement.executeQuery();

            while(resultSet.next()){
                searchResult.add(new DataItem(resultSet.getInt("year"), resultSet.getFloat("avg_rating")));
            }

            return searchResult;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RetrieveDataException("Could not retrieve rating over the years.");
        } finally {
            connection.close();
        }
    }

    @SneakyThrows
    @Override
    public List<Person> getBestRatedDirectors() {
        Connection connection = null;
        List<Person> searchResult = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement readStatement = connection.prepareStatement("SELECT name, rating, sum_votes FROM dbo.people INNER JOIN (SELECT TOP 5 person_id, AVG(rating) as rating, SUM(votes) as sum_votes\n" +
                    "FROM dbo.ratings INNER JOIN dbo.directors \n" +
                    "on dbo.directors.movie_id = dbo.ratings.movie_id group by person_id order by rating desc) temp ON \n" +
                    "dbo.people.id = temp.person_id");

            ResultSet resultSet = readStatement.executeQuery();

            while(resultSet.next()){
                searchResult.add(new Person(resultSet.getString("name"), resultSet.getFloat("rating"), resultSet.getInt("sum_votes")));
            }

            return searchResult;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RetrieveDataException("Could not retrieve best rated directors.");
        } finally {
            connection.close();
        }
    }

    @SneakyThrows
    @Override
    public List<Person> getBestRatedActors() {
        Connection connection = null;
        List<Person> searchResult = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement readStatement = connection.prepareStatement("SELECT name, rating, sum_votes FROM dbo.people INNER JOIN (SELECT TOP 5 person_id, AVG(rating) as rating, SUM(votes) as sum_votes  \n" +
                    "FROM dbo.ratings INNER JOIN dbo.stars \n" +
                    "on dbo.stars.movie_id = dbo.ratings.movie_id group by person_id order by rating desc) temp ON \n" +
                    "dbo.people.id = temp.person_id");

            ResultSet resultSet = readStatement.executeQuery();

            while(resultSet.next()){
                searchResult.add(new Person(resultSet.getString("name"), resultSet.getFloat("rating"), resultSet.getInt("sum_votes")));
            }

            return searchResult;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RetrieveDataException("Could not retrieve best rated actors.");
        } finally {
            connection.close();
        }
    }

}
