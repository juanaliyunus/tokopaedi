package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserAccount loadUserById(String id);
    UserAccount getByContext();
}
