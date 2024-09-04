package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.model.entity.UserAccount;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, String> {
    @Cacheable("userAccounts")
    Optional<UserAccount> findByUsername(String username);
}
