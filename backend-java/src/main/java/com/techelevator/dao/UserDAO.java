package com.techelevator.dao;

import com.techelevator.model.User;

public interface UserDAO {

    User findByUsername(String username);

    boolean usernameExists(String username);
    
    boolean create(String username, String password, String role);
    
    void updateUserRole (String username);
}
