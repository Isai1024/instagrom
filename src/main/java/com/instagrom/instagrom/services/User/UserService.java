package com.instagrom.instagrom.services.User;

import javax.naming.AuthenticationException;

import org.springframework.dao.DuplicateKeyException;

import com.instagrom.instagrom.dto.user.NewUserRequest;
import com.instagrom.instagrom.models.User;

public interface UserService {

    /**
     * Authenticate a user with the given username and password.
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @return a JWT token if authentication is successful
     * @throws AuthenticationException if authentication fails
     */
    String authenticate(String username, String password) throws AuthenticationException;

     /**
     * Create a user and return the user id of the new user
     * 
     * @param params properties used to generate a new user
     * @return the id of the new user
     * @throws DuplicateKeyException the email is already used
     */
    long createUser(NewUserRequest newUser) throws DuplicateKeyException;
    
    /**
     * Get the user stored on the db
     * 
     * @return a list of user
     */
    User getInfoUser(String username);

}