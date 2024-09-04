package com.enigmacamp.tokonyadia.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponse {
    private String id;
    private String name;
    private String address;
    private String position;
    private String email;
    private Boolean status;
    private ImageResponse image;
}
