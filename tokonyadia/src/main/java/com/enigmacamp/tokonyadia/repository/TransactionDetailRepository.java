package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.model.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail,String> {
}
