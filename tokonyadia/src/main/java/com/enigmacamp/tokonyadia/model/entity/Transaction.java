package com.enigmacamp.tokonyadia.model.entity;

import com.enigmacamp.tokonyadia.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.lang.constant.Constable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantTable.TRANSACTION)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "transaction")
    @JsonManagedReference
    private List<TransactionDetail> transactionDetails;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "trans_date", updatable = false)
    private Date transDate;

    @OneToOne
    @JoinColumn(name = "payment_id", unique = true)
    private Payment payment;
}


