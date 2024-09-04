package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.model.entity.Product;
import com.enigmacamp.tokonyadia.repository.ProductRepository;
import com.enigmacamp.tokonyadia.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    ProductRequest productRequest;
    @BeforeEach
    void beforeEach() {
        ProductRequest productRequest = ProductRequest.builder()
                .name("product_test")
                .stock(10)
                .price(150L)
                .build();
    }

    @Test
    @DisplayName("Test save product success")
    void createSuccess() {


        ProductResponse expectedResponse = ProductResponse.builder()
                .id("p1")
                .name("product_test")
                .stock(10)
                .price(150L)
                .build();

        Product product = Product.builder()
                .id(expectedResponse.getId())
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .build();

        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product);
        ProductResponse actualProduct = productService.create(productRequest);

        assertNotNull(actualProduct);
        assertEquals(expectedResponse, actualProduct);
    }

    @Test
    @DisplayName("Test save product fail")
    void createFail() {
        when(productRepository.saveAndFlush(any(Product.class))).thenThrow(new DataIntegrityViolationException("Failed to save product"));

        try {
            productService.create(productRequest);
            verify(productRepository, times(1)).saveAndFlush(any(Product.class));
            fail();
        } catch (DataIntegrityViolationException e) {
            assertEquals("Failed to save product", e.getMessage());
        }
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void updatePut() {
    }

    @Test
    void updatePatch() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getProductById() {
    }
}