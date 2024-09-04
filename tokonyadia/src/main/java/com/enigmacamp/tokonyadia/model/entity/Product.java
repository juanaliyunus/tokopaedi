package com.enigmacamp.tokonyadia.model.entity;

import com.enigmacamp.tokonyadia.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantTable.PRODUCT)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false, columnDefinition = "BIGINT CHECK (price >= 0)")
    private Long price;

    @Column(name = "stock", nullable = false, columnDefinition = "INT CHECK (stock >= 0)")
    private Integer stock;

    @OneToOne
    @JoinColumn(name = "image_id", unique = true)
    private Image image;
}
