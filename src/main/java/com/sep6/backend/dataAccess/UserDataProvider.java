package com.sep6.backend.dataAccess;

import com.sep6.backend.model.User;
import lombok.SneakyThrows;

import java.sql.*;

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

            PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM dbo.users WHERE email=\'" +
                    user.getEmail() + "\'AND password=\'" + user.password() + "\';");

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
}
