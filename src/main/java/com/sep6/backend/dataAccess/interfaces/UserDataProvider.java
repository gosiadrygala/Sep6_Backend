package com.sep6.backend.dataAccess.interfaces;

import com.sep6.backend.model.User;

public interface UserDataProvider {
    User login(User user);
    String register(User user);
}
