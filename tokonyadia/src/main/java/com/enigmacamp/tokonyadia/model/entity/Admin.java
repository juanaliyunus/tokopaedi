package com.enigmacamp.tokonyadia.model.entity;

import com.enigmacamp.tokonyadia.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.ADMIN)
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "position")
    private String position;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    private Boolean status;
    @OneToOne
    @JoinColumn(name = "image_id", unique = true)
    private Image image;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_acount_id", unique = true)
    private UserAccount userAccount;
}
