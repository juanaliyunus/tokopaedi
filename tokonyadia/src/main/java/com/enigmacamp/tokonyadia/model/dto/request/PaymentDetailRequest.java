package com.enigmacamp.tokonyadia.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDetailRequest {
    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("gross_amount")
    private Long amount;
}
