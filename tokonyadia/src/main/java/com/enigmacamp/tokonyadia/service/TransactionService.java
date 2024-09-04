package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.TransactionRequest;
import com.enigmacamp.tokonyadia.model.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse create(TransactionRequest transactionRequest);
}
