package com.enigmacamp.tokonyadia.model.entity;

import com.enigmacamp.tokonyadia.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantTable.CUSTOMER)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "mobile_phone_no")
    private String mobilePhoneNo;

    @Column(name = "address")
    private String address;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Column(name = "status")
    private Boolean status;

    @OneToOne
    @JoinColumn(name = "image_id", unique = true)
    private Image image;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_acount_id", unique = true)
    private UserAccount userAccount;
}
