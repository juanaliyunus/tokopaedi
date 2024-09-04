package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.entity.UserAccount;
import com.enigmacamp.tokonyadia.model.entity.User;
import com.enigmacamp.tokonyadia.repository.UserRepository;
import com.enigmacamp.tokonyadia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public UserAccount loadUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public UserAccount getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getPrincipal().toString())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}