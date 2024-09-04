package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.constant.ResponseMessage;
import com.enigmacamp.tokonyadia.model.dto.request.SearchCustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.request.UpdateCustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.dto.response.PagingResponse;
import com.enigmacamp.tokonyadia.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
@SecurityRequirement(name = "Bearer Authentication")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;
    private final ObjectMapper objectMapper;

    @GetMapping(path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String id) {
        CustomerResponse customer = customerService.getOneById(id);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customer)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomer(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "q", required = false) String query
    ) {
        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .page(Math.max(page - 1, 0))
                .size(size)
                .query(query)
                .build();

        Page<CustomerResponse> customers = customerService.getAll(request);
        PagingResponse paging = PagingResponse.builder()
                .totalPages(customers.getTotalPages())
                .totalElement(customers.getTotalElements())
                .page(page)
                .size(size)
                .hasPrevious(customers.hasPrevious())
                .hasNext(customers.hasNext())
                .build();

        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .paging(paging)
                .data(customers.getContent())
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateCustomer(
            @RequestPart(name = "customer") String jsonCustomer,
            @RequestPart(name = "image") MultipartFile image
    ) {
        try {
            UpdateCustomerRequest request = objectMapper.readValue(jsonCustomer, new TypeReference<>() {
            });
            CustomerResponse customer = customerService.update(request);
            CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                    .data(customer)
                    .build();
            return ResponseEntity.ok(response);

        } catch (JsonProcessingException e) {
            log.error("error: {}", e.getLocalizedMessage());
            CommonResponse<String> response = CommonResponse.<String>builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(ResponseMessage.ERROR_INTERNAL_SERVER)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> updateStatusCustomer(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "status") Boolean status
    ) {
        customerService.updateStatusById(id, status);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id) {
        customerService.deleteById(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }
}
