package com.spring.security.demo.user.impl;

import com.spring.security.demo.commons.ApiResponse;
import com.spring.security.demo.exception.EntityNotFoundException;
import com.spring.security.demo.role.ERole;
import com.spring.security.demo.role.IRoleService;
import com.spring.security.demo.user.*;
import com.spring.security.demo.user.hateoas.UserModel;
import com.spring.security.demo.user.hateoas.UserModelAssembler;
import com.spring.security.demo.util.UuidGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;
    private final UserModelAssembler userModelAssembler;

    public UserServiceImpl(IUserRepository userRepository, PasswordEncoder passwordEncoder, IRoleService roleService, UserModelAssembler userModelAssembler) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userModelAssembler = userModelAssembler;
    }

    @Override
    public ResponseEntity<ApiResponse> saveUser(UserSignUpRequest userSignUpRequest) {
        userRepository.save(User.builder()
                .enabled(true)
                .firstName(userSignUpRequest.getFirstName())
                .lastName(userSignUpRequest.getLastName())
                .userName(userSignUpRequest.getUserName())
                .uuid(UuidGenerator.generateRandomString(12))
                .password(passwordEncoder.encode(userSignUpRequest.getPassword()))
                .roles(Collections.singletonList(roleService.findRoleByName(ERole.ROLE_USER)))
                .build());
        return ResponseEntity.ok(
                ApiResponse.builder().sucess(true)
                        .message("User saved").build()
        );
    }

    @Override
    public ResponseEntity<UserModel> getUser(Authentication authentication) {
        return ResponseEntity.ok(
                userModelAssembler.toModel(
                        userRepository.findByUserName(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"))
                ).add(linkTo(methodOn(UserController.class)
                        .getAllUsers(0, 20, null))
                        .withRel("users"))
        );
    }

    @Override
    public ResponseEntity<PagedModel<?>> getAllUsers(int page, int size, PagedResourcesAssembler<User> userPagedResourcesAssembler) {
        Page<User> pagedUsers = userRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));

        if (pagedUsers.hasContent()) {
            return ResponseEntity.ok(userPagedResourcesAssembler
                    .toModel(pagedUsers, userModelAssembler));
        }
        return ResponseEntity.ok(userPagedResourcesAssembler.toEmptyModel(pagedUsers, UserModel.class));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User member = userRepository.findByUserName(username).orElseThrow(() ->
                new RuntimeException("Wrong credentials")
        );
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        member.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName().name())));
        return new org.springframework.security.core.userdetails.User(member.getUserName(), member.getPassword(), member.isEnabled(), true, true, true, authorities);
    }
}
