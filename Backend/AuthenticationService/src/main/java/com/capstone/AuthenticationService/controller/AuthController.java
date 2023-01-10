package com.capstone.AuthenticationService.controller;

import com.capstone.AuthenticationService.exception.UserAlreadyExistsException;
import com.capstone.AuthenticationService.exception.UserNotFoundException;
import com.capstone.AuthenticationService.model.User;
import com.capstone.AuthenticationService.service.AuthenticationService;
import com.capstone.AuthenticationService.token.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    // Dependent On
    @Autowired
    private AuthenticationService service;

    @Autowired
    private JWTGenerator tokenService;

    // http://localhost:8080/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistsException
    {
        return new ResponseEntity<>(service.saveUser(user), HttpStatus.OK);
    }

    // http://localhost:8080/auth/login
    @PostMapping("/login")
    public ResponseEntity<?>logInProcess(@RequestBody User user) throws UserNotFoundException
    {
        User checkForUser = service.logInCheck(user.getEmail(),user.getPassword());

        // Getting token for valid user
        if(checkForUser != null)
        {
            Map<String,String> key = tokenService.generateToken(checkForUser);
            return new ResponseEntity<>(key,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Authentication failed!",HttpStatus.NOT_FOUND);
    }
}
