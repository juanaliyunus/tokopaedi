package com.enigmacamp.tokonyadia.model.dto.response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String userId;
    private String username;
    private String token;
    private List<String> roles;
}
