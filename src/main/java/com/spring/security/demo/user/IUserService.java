package com.spring.security.demo.user;

import com.spring.security.demo.commons.ApiResponse;
import com.spring.security.demo.user.hateoas.UserModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface IUserService{
    ResponseEntity<ApiResponse> saveUser(UserSignUpRequest userSignUpRequest);
    ResponseEntity<UserModel> getUser(Authentication authentication);
    ResponseEntity<PagedModel<?>> getAllUsers(int page, int size, PagedResourcesAssembler<User> userPagedResourcesAssembler);

    ResponseEntity<ApiResponse> updateUserRole(String userUuid, String newRole, Authentication currentUser);
}
