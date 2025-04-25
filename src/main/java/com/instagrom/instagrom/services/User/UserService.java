package com.instagrom.instagrom.services.User;

import java.util.NoSuchElementException;

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
     * Update a user and return the user id
     * 
     * @param userId
     * @param userUpdate properties used to update the user
     * @return the id of the user
     * @throws NoSuchElementException the id is invalid
     */
    long updateUser(long userId, NewUserRequest userUpdate);

    /**
     * Update a user and return the user id
     * 
     * @param userId
     * @return the id of the user
     * @throws NoSuchElementException the id is invalid
     */
    long deleteUser(long userId);

    /**
     * Get the user stored on the db
     * 
     * @return a list of user
     */
    User getInfoUser(String username);

}