package com.spring.security.demo.user.impl;

import com.spring.security.demo.commons.ApiResponse;
import com.spring.security.demo.role.ERole;
import com.spring.security.demo.role.IRoleService;
import com.spring.security.demo.user.IUserRepository;
import com.spring.security.demo.user.IUserService;
import com.spring.security.demo.user.User;
import com.spring.security.demo.user.UserSignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;

    public UserServiceImpl(IUserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           IRoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public ResponseEntity<ApiResponse> saveUser(UserSignUpRequest userSignUpRequest) {
        userRepository.save(User.builder()
                .enabled(true)
                .firstName(userSignUpRequest.getFirstName())
                .lastName(userSignUpRequest.getLastName())
                .userName(userSignUpRequest.getUserName())
                .password(passwordEncoder.encode(userSignUpRequest.getPassword()))
                .roles(Collections.singletonList(roleService.findRoleByName(ERole.ROLE_USER)))
                .build());
        return ResponseEntity.ok(
                ApiResponse.builder().sucess(true)
                        .message("User saved").build()
        );
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
