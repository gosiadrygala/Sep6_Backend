package com.sep6.backend.services;

import com.sep6.backend.dataAccess.UserDataProvider;
import com.sep6.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDataProvider userDataProvider;

    @Autowired
    public UserService(UserDataProvider userDataProvider){
        this.userDataProvider = userDataProvider;
    }

    public User login(User user) {
        return userDataProvider.login(user);
    }

    public String register(User user) {
        return userDataProvider.register(user);
    }
}

