package com.enigmacamp.tokonyadia.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCustomerRequest {
    private String username;
    private String password;
    private String name;
    private String email;
    private String mobilePhoneNo;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
