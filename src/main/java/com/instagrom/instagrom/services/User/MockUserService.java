package com.instagrom.instagrom.services.User;

import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.instagrom.instagrom.config.JWTUtil;
import com.instagrom.instagrom.dto.user.NewUserRequest;
import com.instagrom.instagrom.models.User;
import com.instagrom.instagrom.repository.UserRepository;

@Service
public class MockUserService implements UserService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(MockUserService.class);
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String authenticate(String username, String password) throws AuthenticationException {
        User user = userRepository.findByEmail(username);

        if(user == null){
            throw new AuthenticationException("The user does not exist");
        }

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new AuthenticationException("The username or the password are invalid");
        }

        this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = jwtUtil.generateToken(user);
        
        return token;
    }

    @Override
    public User getInfoUser(String username) {
        try{
            return this.userRepository.findByEmail(username);
        }catch(Exception e){
            this.logger.error("Fail at get the user stored", e);
            return new User();
        }
    }

    @Override
    public long createUser(NewUserRequest newUser) throws DuplicateKeyException {
        
        User userEmail = this.userRepository.findByEmail(newUser.getEmail());

        if(userEmail != null){
            throw new DuplicateKeyException("The email is already used");
        }
        
        // * encode the password
        var passwordEncode = passwordEncoder.encode(newUser.getPassword());

        try {
            Instant instant = LocalDateTime.now().toInstant(ZoneOffset.of("-06:00"));
            Date date = Date.from(instant);

            // * create the user and save
            var user = new User();
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setUsername(newUser.getUsername());
            user.setPassword(passwordEncode);
            user.setCreatedAt(date);
            user.setUpdatedAt(date);

            userRepository.saveAndFlush(user);
            
            return user.getId();

        } catch (Exception e) {
            throw new RuntimeException("Fail at stored the new user", e);
        }

    }

}
