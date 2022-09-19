package com.spring.security.demo.role.impl;

import com.spring.security.demo.exception.EntityNotFoundException;
import com.spring.security.demo.role.ERole;
import com.spring.security.demo.role.IRoleRepository;
import com.spring.security.demo.role.IRoleService;
import com.spring.security.demo.role.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;

    public RoleServiceImpl(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(ERole roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(
                ()-> new EntityNotFoundException("Role not found")
        );
    }
}
