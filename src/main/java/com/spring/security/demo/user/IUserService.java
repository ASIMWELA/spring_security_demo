package com.spring.security.demo.user;

import org.springframework.http.ResponseEntity;

public interface IUserService{
    ResponseEntity<User> saveUser(UserSignUpRequest userSignUpRequest);
}
