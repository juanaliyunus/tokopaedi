package com.enigmacamp.tokonyadia.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private String id;
    private String name;
    private String email;
    private String mobilePhoneNo;
    private String address;
    private Boolean status;
    private String userAccountId;
    private ImageResponse image;
}
