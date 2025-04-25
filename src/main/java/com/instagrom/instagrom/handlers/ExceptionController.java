package com.instagrom.instagrom.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.instagrom.instagrom.dto.GeneralResponse;

@ControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler(value = { UsernameNotFoundException.class })
    public ResponseEntity<Object> handleUsernameNotFoundException(Exception ex) {

        var responseData = new GeneralResponse<>();
        responseData.setTitle("Username Not Found");
        responseData.setMessage(ex.getMessage());
        
        return ResponseEntity.status(400).body(responseData);

    }

    @ExceptionHandler(value = { JWTDecodeException.class })
    public ResponseEntity<Object> handleJWTDecodeException(Exception ex) {

        var responseData = new GeneralResponse<>();
        responseData.setTitle("Incomplete token.");
        responseData.setMessage(ex.getMessage());
        
        return ResponseEntity.status(401).body(responseData);

    }

    @ExceptionHandler(value = { SignatureVerificationException.class })
    public ResponseEntity<Object> handleSignatureVerificationException(Exception ex) {

        var responseData = new GeneralResponse<>();
        responseData.setTitle("Signature invalid.");
        responseData.setMessage(ex.getMessage());
        
        return ResponseEntity.status(401).body(responseData);

    }

}
