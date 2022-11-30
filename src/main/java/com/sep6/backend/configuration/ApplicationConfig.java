package com.sep6.backend.configuration;

import com.sep6.backend.dataAccess.UserDataProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("${database.url}")
    private String databaseUrl;
    @Value("${database.user}")
    private String databaseUser;
    @Value("${database.password}")
    private String databasePassword;

    @Bean
    public UserDataProvider getUserDataProvider(){
        return new UserDataProvider(databaseUrl, databaseUser, databasePassword);
    }

}
