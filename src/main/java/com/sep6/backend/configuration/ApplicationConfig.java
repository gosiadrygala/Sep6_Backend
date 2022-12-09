package com.sep6.backend.configuration;

import com.sep6.backend.dataAccess.OMDBMovieDataProvider;
import com.sep6.backend.dataAccess.interfaces.ExtendedMovieDataProvider;
import com.sep6.backend.dataAccess.interfaces.MoviesDataProvider;
import com.sep6.backend.dataAccess.SQLMoviesDataProvider;
import com.sep6.backend.dataAccess.SQLUserDataProvider;
import com.sep6.backend.dataAccess.interfaces.UserDataProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("${database.url}")
    private String databaseUrl;
    @Value("${database.name}")
    private String databaseName;
    @Value("${database.user}")
    private String databaseUser;
    @Value("${database.password}")
    private String databasePassword;
    @Value("${omdb.apikey}")
    private String apikey;

    @Bean
    public UserDataProvider getUserDataProvider(){
        String url = databaseUrl+ ";database=" + databaseName;
        return new SQLUserDataProvider(url, databaseUser, databasePassword);
    }

    @Bean
    public MoviesDataProvider getMoviesDataProvider(){
        String url = databaseUrl+ ";database=" + databaseName;
        return new SQLMoviesDataProvider(url, databaseUser, databasePassword);
    }

    @Bean
    public ExtendedMovieDataProvider getExtendedMovieDataProvider(){
        return new OMDBMovieDataProvider(apikey);
    }

}
