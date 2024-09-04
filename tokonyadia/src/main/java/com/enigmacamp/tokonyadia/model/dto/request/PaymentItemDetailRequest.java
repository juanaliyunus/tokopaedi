package com.enigmacamp.tokonyadia.model.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentItemDetailRequest {
    private String id;
    private Long price;
    private Integer quantity;
    private String name;
}
