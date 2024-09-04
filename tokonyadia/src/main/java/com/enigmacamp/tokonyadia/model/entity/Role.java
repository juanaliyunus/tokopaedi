package com.enigmacamp.tokonyadia.model.entity;

import com.enigmacamp.tokonyadia.constant.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "m_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}


