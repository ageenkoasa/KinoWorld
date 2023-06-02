package com.example.kino.service;

import com.example.kino.model.Role;
import com.example.kino.model.User;
import com.example.kino.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class RoleService {
    private final RoleRepository roleRepo;

    public RoleService(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role findByName(String name) {
        return roleRepo.findByName(name);
    }

    public void setUserRole(User user) {
        this.setRole(user, "USER");
    }

    public void setAdminRole(User user) {
        this.setRole(user, "ADMIN");
    }

    public void setRole(User user, String roleName) {
        Role role = saveRole(roleName);

        if (user.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton(role));
        } else {
            Set<Role> roles = user.getRoles();
            roles.add(role);
            user.setRoles(roles);
        }
    }

    public Role saveRole(String name) {
       Role userRole = roleRepo.findByName(name);
        Role role = null;
        if (userRole == null) {
            role = new Role(name);
            role = roleRepo.save(role);
        }
        return role;
    }
}
