package com.spring.security.demo.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSignUpRequest {
    @NotEmpty(message = "User name is required")
    String userName;
    @NotEmpty(message = "firstName  is required")
    String firstName;
    @NotEmpty(message = "lastName  is required")
    String lastName;
    @NotEmpty(message = "Phone number  is required")
    String phoneNumber;
    @NotEmpty(message = "password number is required")
    String password;
}
