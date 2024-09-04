package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.model.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.PRODUCT_API, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@SecurityRequirement(name = "Authorization")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> createNewProduct(@RequestBody ProductRequest payload) {
        ProductResponse product = productService.create(payload);
        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("New product added!")
                .data(product)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAllProduct(
            @RequestParam(name = "name", required = false) String name
    ) {
        List<ProductResponse> productList = productService.getAll(name);

        CommonResponse<List<ProductResponse>> response = CommonResponse.<List<ProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("All product data")
                .data(productList)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}") // /api/product/{UUID}
    public ResponseEntity<CommonResponse<ProductResponse>> getProductById(@PathVariable String id) {
        ProductResponse product = productService.getById(id);
        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("New product added!")
                .data(product)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(@RequestBody ProductRequest payload) {
        ProductResponse product = productService.updatePut(payload);
        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("New product added!")
                .data(product)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductResponse>> deleteProductById(@PathVariable String id) {
        productService.deleteById(id);
        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("New product added!")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping()
    public ResponseEntity<CommonResponse<ProductResponse>> updateStock(@RequestBody ProductRequest payload) {
        productService.updatePatch(payload);
        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("New product added!")
                .build();
        return ResponseEntity.ok(response);
    }
}
