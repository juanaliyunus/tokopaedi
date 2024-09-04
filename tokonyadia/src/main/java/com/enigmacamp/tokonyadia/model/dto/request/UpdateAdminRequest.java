package com.enigmacamp.tokonyadia.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAdminRequest {
    @NotBlank
    private String id;
    private String username;
    private String password;
    private String name;
    private String address;
    private String position;
    private String email;
    private MultipartFile image;
}