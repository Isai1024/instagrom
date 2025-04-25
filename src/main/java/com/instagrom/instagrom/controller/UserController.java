package com.instagrom.instagrom.controller;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagrom.instagrom.dto.user.AuthRequest;
import com.instagrom.instagrom.dto.user.AuthResponse;
import com.instagrom.instagrom.dto.GeneralResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Operation(summary = "Authenticate a user", description = "Validates a user's credentials and returns the authentication token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "409", description = "Internal Unhandle Exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class)))
    })
    @PostMapping("/signin")
    public ResponseEntity<Object> authenticate(@RequestBody AuthRequest authRequest) {

        return null;
    }

}
