package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.entity.Payment;
import com.enigmacamp.tokonyadia.model.entity.Transaction;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentService {
    Payment createPayment(Transaction transaction);
    void checkFailedAndUpdatePayments();
}

