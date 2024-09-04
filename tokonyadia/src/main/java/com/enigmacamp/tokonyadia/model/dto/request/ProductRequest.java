package com.enigmacamp.tokonyadia.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private String id;
    private String name;
    private Long price;
    private Integer stock;
}
