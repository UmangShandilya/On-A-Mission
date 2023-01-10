package com.capstone.AuthenticationService.token;

import com.capstone.AuthenticationService.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTGeneratorImplementation implements JWTGenerator{
    @Override
    public Map<String, String> generateToken(User user)
    {
        // OUTCOME
        Map<String, String> token = new HashMap<>();

        // Hash map to store user object
        Map<String, Object> userData = new HashMap<>();
        userData.put("userObject", user);

        //TOKEN GENERATION
        String JWTToken = Jwts.builder()
                .setClaims(userData) // Require a map
                .setIssuedAt(new Date()) //Date issued
                .signWith(SignatureAlgorithm.HS512, "securityKey") // Specifies Algorithm
                .compact();
        token.put("token", JWTToken);
        return token;

    }
}
