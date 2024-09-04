package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.SearchCustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.request.UpdateCustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Customer create(Customer customer);
    CustomerResponse getOneById(String id);
    Customer getById(String id);
    Page<CustomerResponse> getAll(SearchCustomerRequest request);
    CustomerResponse update(UpdateCustomerRequest customer);
    void deleteById(String id);
    void updateStatusById(String id, Boolean status);

    Customer getByToken();
}
