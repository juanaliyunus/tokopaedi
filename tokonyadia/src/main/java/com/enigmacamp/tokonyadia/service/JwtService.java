package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.response.JwtClaims;
import com.enigmacamp.tokonyadia.model.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
