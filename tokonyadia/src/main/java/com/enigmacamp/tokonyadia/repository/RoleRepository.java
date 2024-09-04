package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.constant.UserRole;
import com.enigmacamp.tokonyadia.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(UserRole role);
}
