package com.enigmacamp.tokonyadia.model.dto.request;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    private String username;
    private String password;
}
