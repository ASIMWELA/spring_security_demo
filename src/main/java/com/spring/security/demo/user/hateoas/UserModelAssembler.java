package com.spring.security.demo.user.hateoas;

import com.spring.security.demo.user.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {
    @Override
    public UserModel toModel(User entity) {
        return UserModel.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .userName(entity.getUserName())
                .enabled(entity.isEnabled())
                .build();
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
