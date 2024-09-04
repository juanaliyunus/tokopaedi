package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.*;
import com.enigmacamp.tokonyadia.model.dto.response.LoginResponse;
import com.enigmacamp.tokonyadia.model.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(NewCustomerRequest request);
    RegisterResponse registerAdmin(NewAdminRequest request);
    LoginResponse login(AuthRequest request);

    boolean validateToken();
}
