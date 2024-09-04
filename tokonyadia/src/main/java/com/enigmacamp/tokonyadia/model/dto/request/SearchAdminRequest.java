package com.enigmacamp.tokonyadia.model.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchAdminRequest {
    private Integer page;
    private Integer size;
    private String query;
}

