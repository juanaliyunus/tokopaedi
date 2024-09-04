package com.enigmacamp.tokonyadia.model.dto.response;

import com.enigmacamp.tokonyadia.model.entity.Customer;
import com.enigmacamp.tokonyadia.model.entity.TransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private Customer customer;
    private Date date;
    private List<TransactionDetail> transactionDetails;
    private Long totalPayment;
}
