package com.student.student.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.student.student.entity.StudentEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.key}")
    private String key;

    @Value(("${jwt.issuer}"))
    private String issuer;

    @Value(("${jwt.expiryDate}"))
    private int duration;
    Algorithm algorithm;

    private final String USER_NAME="username";

    @PostConstruct
    public void postConstruct(){
        algorithm = Algorithm.HMAC256(key);

    }
    public String generateToken(StudentEntity entity){
        return JWT.create()
                .withClaim(USER_NAME,entity.getName())
                .withExpiresAt(new Date(System.currentTimeMillis()+duration))
                .withIssuer(issuer)
                .sign(algorithm);

    }


    public String getUser(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();
    }
}
