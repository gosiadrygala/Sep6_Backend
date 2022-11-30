package com.sep6.backend.dataAccess;

import com.sep6.backend.model.User;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataProvider {

    private final String url;
    private final String username;
    private final String password;

    public UserDataProvider(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @SneakyThrows
    public User login(User user) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM dbo.users WHERE username='" +
                    user.getUsername() + "'AND password='" + user.getPassword() + "';");

            ResultSet resultSet = readStatement.executeQuery();
            if (!resultSet.next()) {
                return new User();
            }

            User.UserBuilder builder = User.builder();
            builder.email(resultSet.getString("email"));
            builder.username(resultSet.getString("username"));
            builder.password("");

            return builder.build();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return new User();
    }

    @SneakyThrows
    public String register(User user) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM dbo.users WHERE username='" +
                    user.getUsername() + "'OR email='" + user.getEmail() + "';");

            ResultSet resultSet = readStatement.executeQuery();
            if (!resultSet.next()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dbo.users (email, username, password)" +
                        "VALUES ('" + user.getEmail() + "', '" + user.getUsername()
                        + "','" + user.getPassword() + "');");
                preparedStatement.executeUpdate();
                return "Register successful";
            }

            boolean usernameExists = user.getUsername().equals(resultSet.getString("username"));
            boolean emailExists = user.getEmail().equals(resultSet.getString("email"));

            if(usernameExists && emailExists)
                return "User already exists";
            else if(usernameExists)
                return "Username already exists";
            else if(emailExists)
                return "Email already exists";

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return "";
    }
}
