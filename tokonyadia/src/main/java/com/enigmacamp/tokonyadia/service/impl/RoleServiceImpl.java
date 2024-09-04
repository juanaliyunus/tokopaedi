package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.constant.UserRole;
import com.enigmacamp.tokonyadia.model.entity.Role;
import com.enigmacamp.tokonyadia.repository.RoleRepository;
import com.enigmacamp.tokonyadia.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(UserRole role) {
        Optional<Role> optionalRole = roleRepository.findByRole(role);
        // role available return it
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }

        // role not available create new
        Role currentRole = Role.builder()
                .role(role)
                .build();

        return roleRepository.saveAndFlush(currentRole);
    }
}
