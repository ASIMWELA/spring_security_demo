package com.spring.security.demo.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.demo.util.DecodedTokenPayload;
import com.spring.security.demo.util.TokenUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final TokenUtility tokenUtility;
    public CustomAuthorizationFilter(TokenUtility tokenUtility) {
        this.tokenUtility = tokenUtility;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/v1/auth/login") || request.getServletPath().equals("/api/v1/auth/refresh-token") ){
            filterChain.doFilter(request, response);
        }else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                try{
                    String token = authorizationHeader.substring("Bearer ".length());
                    DecodedTokenPayload decodedTokenPayload = tokenUtility.verifyToken(token);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(decodedTokenPayload.getUserName(), null, decodedTokenPayload.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request, response);
                }catch (Exception exception){
                    Map<String, String> error = new HashMap<>();
                    if(exception instanceof  TokenExpiredException){
                       error.put("error", "The token is expired");
                    }else if(exception instanceof JWTDecodeException || exception instanceof NullPointerException){
                      error.put("error", "The token is invalid");
                    }else {
                        error.put("error", exception.getMessage());
                    }
                    response.setContentType(APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            }else{
                filterChain.doFilter(request, response);
            }
        }
    }
}
