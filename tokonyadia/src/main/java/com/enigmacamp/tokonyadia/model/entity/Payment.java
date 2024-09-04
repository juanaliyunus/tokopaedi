package com.enigmacamp.tokonyadia.model.entity;

import com.enigmacamp.tokonyadia.constant.ConstantTable;
import com.enigmacamp.tokonyadia.constant.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.PAYMENT)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "token")
    private String token;

    @Column(name = "redirect_url")
    private String redirectUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus;

    @OneToOne(mappedBy = "payment") // ini harus sama dengan attribute di transaction
    private Transaction transaction;
}
