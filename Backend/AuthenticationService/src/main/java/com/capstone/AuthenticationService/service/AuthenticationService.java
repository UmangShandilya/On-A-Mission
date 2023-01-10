package com.capstone.AuthenticationService.service;

import com.capstone.AuthenticationService.exception.UserAlreadyExistsException;
import com.capstone.AuthenticationService.exception.UserNotFoundException;
import com.capstone.AuthenticationService.model.User;

public interface AuthenticationService
{
    // Method to save user detail on registration
    User saveUser(User user) throws UserAlreadyExistsException;

    // Method to check for user's credentials
    User logInCheck(String email, String password) throws UserNotFoundException;
}
