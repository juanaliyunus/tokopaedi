package com.enigmacamp.tokonyadia.utils.specifications;

import com.enigmacamp.tokonyadia.model.entity.Admin;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AdminSpecification {
    public static Specification<Admin> getSpecification(String q) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + q.toLowerCase() + "%"));
            predicates.add(cb.like(cb.lower(root.get("address")), "%" + q.toLowerCase() + "%"));
            predicates.add(cb.like(cb.lower(root.get("position")), "%" + q.toLowerCase() + "%"));
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + q.toLowerCase() + "%"));
            return cb.or(predicates.toArray(new Predicate[]{}));
        };
    }
}