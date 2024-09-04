package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.constant.TransactionStatus;
import com.enigmacamp.tokonyadia.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findAllByTransactionStatusIn(Collection<TransactionStatus> transactionStatus);
}
