package com.spring.security.demo.user.hateoas;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserModel extends RepresentationModel<UserModel> {
    String firstName;
    String lastName;
    String userName;
    boolean enabled;
}
