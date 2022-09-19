package com.spring.security.demo.user;

import com.spring.security.demo.commons.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface IUserService{
    ResponseEntity<ApiResponse> saveUser(UserSignUpRequest userSignUpRequest);
}
