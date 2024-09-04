package com.enigmacamp.tokonyadia.model.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCustomerRequest {
    private String query;
    private Boolean status;
    private Integer page;
    private Integer size;
}
