package com.capstone.AuthenticationService.token;

import com.capstone.AuthenticationService.model.User;

import java.util.Map;

public interface JWTGenerator
{
    Map<String,String> generateToken(User user);
}
