package com.capstone.AuthenticationService.service;

import com.capstone.AuthenticationService.exception.UserAlreadyExistsException;
import com.capstone.AuthenticationService.exception.UserNotFoundException;
import com.capstone.AuthenticationService.model.User;
import com.capstone.AuthenticationService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService
{
    // Dependent On

    @Autowired
    private UserRepository repository;


    @Override
    public User saveUser(User user) throws UserAlreadyExistsException
    {
        // Checkpoint
        if(repository.existsById(user.getEmail()))
            throw new UserAlreadyExistsException();

        return repository.save(user);
    }

    @Override
    public User logInCheck(String email, String password) throws UserNotFoundException {
        // Checkpoint
        if(!repository.existsById(email))
            throw new UserNotFoundException();
        else
        {
            User registeredUser = repository.findByEmailAndPassword(email,password);
            if(registeredUser != null)
            {
                // Hiding password
                registeredUser.setPassword("");
                return registeredUser;
            }
        }
        return null;
    }
}
