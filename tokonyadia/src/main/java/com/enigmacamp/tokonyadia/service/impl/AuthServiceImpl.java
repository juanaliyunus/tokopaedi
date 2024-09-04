package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.constant.UserRole;
import com.enigmacamp.tokonyadia.model.dto.request.AuthRequest;
import com.enigmacamp.tokonyadia.model.dto.request.NewAdminRequest;
import com.enigmacamp.tokonyadia.model.dto.request.NewCustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.LoginResponse;
import com.enigmacamp.tokonyadia.model.dto.response.RegisterResponse;
import com.enigmacamp.tokonyadia.model.entity.Admin;
import com.enigmacamp.tokonyadia.model.entity.Customer;
import com.enigmacamp.tokonyadia.model.entity.Role;
import com.enigmacamp.tokonyadia.model.entity.UserAccount;
import com.enigmacamp.tokonyadia.repository.UserRepository;
import com.enigmacamp.tokonyadia.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${app.tokonyadiashop.username.superadmin}")
    private String superAdminUsername;
    @Value("${app.tokonyadiashop.password.superadmin}")
    private String superAdminPassword;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initSuperAdmin() {
        Optional<UserAccount> currentUser = userRepository.findByUsername(superAdminUsername);
        if (currentUser.isPresent()) return;

        Role superAdmin = roleService.getOrSave(UserRole.ROLE_SUPER_ADMIN);
        Role adminRole = roleService.getOrSave(UserRole.ADMIN);
        Role customer = roleService.getOrSave(UserRole.CUSTOMER);

        UserAccount account = UserAccount.builder()
                .username(superAdminUsername)
                .password(passwordEncoder.encode(superAdminPassword))
                .role(List.of(superAdmin, adminRole, customer))
                .isEnable(true)
                .build();
        userRepository.saveAndFlush(account);

        Admin admin = Admin.builder()
                .status(true)
                .userAccount(account)
                .build();
        adminService.create(admin);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(NewCustomerRequest request) throws DataIntegrityViolationException {
        Role role = roleService.getOrSave(UserRole.CUSTOMER);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(List.of(role))
                .isEnable(true)
                .build();

        userRepository.saveAndFlush(account);

        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobilePhoneNo(request.getMobilePhoneNo())
                .address(request.getAddress())
                .birthDate(request.getBirthDate())
                .status(true)
                .userAccount(account)
                .build();
        customerService.create(customer);

        List<String> roles = account.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(roles)
                .build();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerAdmin(NewAdminRequest request) {
        Role roleAdmin = roleService.getOrSave(UserRole.ADMIN);
        Role roleCustomer = roleService.getOrSave(UserRole.CUSTOMER);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(List.of(roleAdmin, roleCustomer))
                .isEnable(true)
                .build();

        userRepository.saveAndFlush(account);

        Admin admin = Admin.builder()
                .name(request.getName())
                .address(request.getAddress())
                .position(request.getPosition())
                .email(request.getEmail())
                .status(true)
                .userAccount(account)
                .build();
        adminService.create(admin);

        Customer customer = Customer.builder()
                .status(true)
                .userAccount(account)
                .build();
        customerService.create(customer);

        List<String> roles = account.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(roles)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();
        String token = jwtService.generateToken(userAccount);
        return LoginResponse.builder()
                .userId(userAccount.getId())
                .username(userAccount.getUsername())
                .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .token(token)
                .build();
    }

    @Override
    public boolean validateToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userRepository.findByUsername(authentication.getPrincipal().toString())
                .orElse(null);
        return userAccount != null;
    }
}
