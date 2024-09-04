package com.enigmacamp.tokonyadia.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerRequest {
    @NotBlank
    private String id;
    private String username;
    private String password;
    private String name;
    private String mobilePhoneNo;
    private String email;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthDate;
    private MultipartFile image;
}
