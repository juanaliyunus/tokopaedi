package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.model.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);

    List<ProductResponse> getAll(String name);

    ProductResponse getById(String id);

    ProductResponse updatePut(ProductRequest request);

    ProductResponse updatePatch(ProductRequest request);

    void deleteById(String id);
    Product getProductById(String id);
}
