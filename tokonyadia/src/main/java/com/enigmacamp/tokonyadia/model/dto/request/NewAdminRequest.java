package com.enigmacamp.tokonyadia.model.dto.request;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewAdminRequest {
    private String username;
    private String password;
    private String name;
    private String address;
    private String position;
    private String email;
    private Boolean status;
}
