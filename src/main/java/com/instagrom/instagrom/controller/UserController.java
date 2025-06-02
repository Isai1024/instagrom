package com.instagrom.instagrom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagrom.instagrom.dto.user.AuthRequest;
import com.instagrom.instagrom.dto.user.AuthResponse;
import com.instagrom.instagrom.dto.user.NewUserRequest;
import com.instagrom.instagrom.services.User.UserService;
import com.smattme.requestvalidator.RequestValidator;
import com.instagrom.instagrom.dto.GeneralResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @Operation(summary = "Authenticate a user", description = "Validates a user's credentials and returns the authentication token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "409", description = "Internal Unhandle Exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class)))
    })
    @PostMapping("/signin")
    public ResponseEntity<Object> authenticate(@RequestBody AuthRequest authRequest) {

        try {
            logger.info("Attempt to authenticate the user:{} with pass:{}",
                    authRequest.getUsername(),
                    authRequest.getPassword());

            // * authenticate the user and return the authtoken
            var userToken = userService.authenticate(authRequest.getUsername(), authRequest.getPassword());
            
            // * take the user's name
            var userName = userService.getInfoUser(authRequest.getUsername()).getName();

            // * prepare the response
            var responseData = new AuthResponse();
            responseData.setUsername(authRequest.getUsername());
            responseData.setName(userName);
            responseData.setToken(userToken);
            return new ResponseEntity<>(responseData, HttpStatus.OK);

        } catch (AuthenticationException ae) {
            logger.info("The credentials are invalid: {}", ae.getMessage());

            var responseData = new GeneralResponse<>();
            responseData.setTitle("The credentials are invalid.");
            responseData.setMessage(ae.getMessage());
            return ResponseEntity.status(401).body(responseData);
        } catch (Exception e) {
            logger.error("An error occurred while attempting to authenticate the user", e);

            // * prepare the resppnse
            var responseData = new GeneralResponse<>();
            responseData.setTitle("Unhandle exception at attempting to validate the user.");
            responseData.setMessage(e.getMessage());
            return ResponseEntity.status(409).body(responseData);
        }
    }

    @Operation(summary = "Create a new User", description = "Create a new user with the given properties.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The user was create", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "422", description = "The properties of the user are not valid.")
    })
    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody NewUserRequest newUser) {

        System.out.println("New user: " + newUser.toString());

        // * request required
        Map<String, String> rules = new HashMap<>();
            rules.put("name", "required");
            rules.put("email", "required|email");
            rules.put("password", "required");

        // * validate the request
        List<String> errors = RequestValidator.validate(newUser, rules);
        if (!errors.isEmpty()) {
            var responseData = new GeneralResponse<>();
            responseData.setTitle("Missing required parameter(s)");
            responseData.setMessage("BAD REQUEST");
            responseData.setData(errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(responseData);
        }

        // * create the user
        long userId = 0;
        try {
            userId = this.userService.createUser(newUser);
        } catch (DuplicateKeyException de) {
            var errorData = new HashMap<String, Object>();
            errorData.put("email", "The email is already used");
            return ResponseEntity.unprocessableEntity().body(errorData);
        }

        // * return the userId
        var responseData = new GeneralResponse<Long>();
        responseData.setTitle("The user was created");
        responseData.setMessage(String.format("The user was created with id %s", userId));
        responseData.setData(userId);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Update the user information", description = "Update the user information with the given properties.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user was updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "422", description = "The properties of the user are not valid.")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody NewUserRequest userUpdate) {
        
        // * request required
        Map<String, String> rules = new HashMap<>();
            rules.put("name", "required");
            rules.put("email", "required|email");

        // * validate the request
        List<String> errors = RequestValidator.validate(userUpdate, rules);
        if (!errors.isEmpty()) {
            var responseData = new GeneralResponse<>();
            responseData.setTitle("Missing required parameter(s)");
            responseData.setMessage("BAD REQUEST");
            responseData.setData(errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(responseData);
        }

        // * update the user
        long userId = 0;
        try {
            userId = this.userService.updateUser(id, userUpdate);
        } catch (NoSuchElementException de) {
            var errorData = new HashMap<String, Object>();
            errorData.put("Id", "The id is invalid");
            return ResponseEntity.unprocessableEntity().body(errorData);
        }

        // * return the userId
        var responseData = new GeneralResponse<Long>();
        responseData.setTitle("The user was updated");
        responseData.setMessage(String.format("The user was updated with id %s", userId));
        responseData.setData(userId);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete the user information with SoftDelete", description = "Delete the user information with SoftDelete.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user was deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "422", description = "The properties of the user are not valid.")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) throws NoSuchElementException{
        
        // * delete the user
        long userId = 0;
        try {
            userId = this.userService.deleteUser(id);
        } catch (NoSuchElementException de) {
            var errorData = new HashMap<String, Object>();
            errorData.put("Id", "The id is invalid");
            return ResponseEntity.unprocessableEntity().body(errorData);
        } catch (IllegalArgumentException ex) {
            var errorData = new HashMap<String, Object>();
            errorData.put("User", "The user is already deleted");
            return ResponseEntity.unprocessableEntity().body(errorData);
        }

        // * return the userId
        var responseData = new GeneralResponse<Long>();
        responseData.setTitle("The user was deleted");
        responseData.setMessage(String.format("The user was deleted with id %s", userId));
        responseData.setData(userId);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

}
