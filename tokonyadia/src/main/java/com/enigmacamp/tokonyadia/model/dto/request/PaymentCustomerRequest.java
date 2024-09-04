package com.enigmacamp.tokonyadia.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCustomerRequest {
    @JsonProperty("first_name")
    private String name;
}
