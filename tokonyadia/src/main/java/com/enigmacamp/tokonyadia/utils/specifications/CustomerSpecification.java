package com.enigmacamp.tokonyadia.utils.specifications;

import com.enigmacamp.tokonyadia.model.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerSpecification {
    public static Specification<Customer> getSpecification(String q) {
        return (root, cq, cb) -> {
            if (!StringUtils.hasText(q)) return cb.conjunction();

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.like(cb.lower(root.get("name")), "%" + q.toLowerCase() + "%"));
            predicates.add(cb.like(cb.lower(root.get("mobilePhoneNo")), "%" + q.toLowerCase() + "%"));
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + q.toLowerCase() + "%"));
            predicates.add(cb.like(cb.lower(root.get("address")), "%" + q.toLowerCase() + "%"));

            Date date = parseDate(q);
            if (date != null) {
                predicates.add(cb.equal(root.get("birthDate"), date));
            }

            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }

    private static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
