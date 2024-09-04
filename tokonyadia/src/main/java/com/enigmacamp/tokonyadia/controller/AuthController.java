package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.model.dto.request.AuthRequest;
import com.enigmacamp.tokonyadia.model.dto.request.NewCustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.dto.response.LoginResponse;
import com.enigmacamp.tokonyadia.model.dto.response.RegisterResponse;
import com.enigmacamp.tokonyadia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIUrl.AUTH_API)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authenticationService;
    private static final Logger logger = LogManager.getLogger(AuthService.class);

    @PostMapping("/register/customer")
    public ResponseEntity<CommonResponse<RegisterResponse>> registerCustumer(@RequestBody NewCustomerRequest request) {
        logger.info("Accessed Endpoint : " + APIUrl.AUTH_API + "/register/customer");

        RegisterResponse response = authenticationService.register(request);

        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully register new customer")
                .data(response)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody AuthRequest request) {
        logger.info("Accessed Endpoint : " + APIUrl.AUTH_API + "/login");
        LoginResponse response = authenticationService.login(request);

        CommonResponse<LoginResponse> commonResponse = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Success Login")
                .data(response)
                .build();
        return ResponseEntity.ok(commonResponse);
    }
}
