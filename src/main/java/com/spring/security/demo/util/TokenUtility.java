package com.spring.security.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class TokenUtility {
    @Value("${app.jwt.secrete}")
    private String jwtSecrete;
    public String generateAccessToken(User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 172800000))
                .withClaim("roles", user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(jwtSecrete));
    }
    public String generateRefreshToken(User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1728000000))
                .sign(Algorithm.HMAC256(jwtSecrete));
    }
    public DecodedTokenPayload verifyToken(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtSecrete)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String userName = decodedJWT.getSubject();
        String [] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role-> authorities.add(new SimpleGrantedAuthority(role)));
        return DecodedTokenPayload.builder()
                .userName(userName)
                .authorities(authorities)
                .build();
    }
}