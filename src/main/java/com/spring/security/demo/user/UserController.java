package com.spring.security.demo.user;

import com.spring.security.demo.commons.ApiResponse;
import com.spring.security.demo.user.hateoas.UserModel;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest){
        return userService.saveUser(userSignUpRequest);
    }


    @GetMapping
    public ResponseEntity<PagedModel<?>> getAllUsers(@PositiveOrZero  @RequestParam(value="page", defaultValue = "0") int page,
                                                      @Positive @RequestParam(value="size", defaultValue = "20") int size,
                                                     PagedResourcesAssembler<User> pagedResourcesAssembler){
        return userService.getAllUsers(page, size, pagedResourcesAssembler);
    }

    @GetMapping("/me")
    public ResponseEntity<UserModel> getUserDetails(Authentication authentication){
        return userService.getUser(authentication);
    }
}
