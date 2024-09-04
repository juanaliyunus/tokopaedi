package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.constant.TransactionStatus;
import com.enigmacamp.tokonyadia.model.dto.request.PaymentCustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.request.PaymentDetailRequest;
import com.enigmacamp.tokonyadia.model.dto.request.PaymentItemDetailRequest;
import com.enigmacamp.tokonyadia.model.dto.request.PaymentRequest;
import com.enigmacamp.tokonyadia.model.entity.Payment;
import com.enigmacamp.tokonyadia.model.entity.Product;
import com.enigmacamp.tokonyadia.model.entity.Transaction;
import com.enigmacamp.tokonyadia.model.entity.TransactionDetail;
import com.enigmacamp.tokonyadia.repository.PaymentRepository;
import com.enigmacamp.tokonyadia.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RestClient restClient;
    private final String SECRET_KEY;
    private final String BASE_URL_SNAP;

    @Autowired
    public PaymentServiceImpl(
            PaymentRepository paymentRepository, RestClient restClient,
            @Value("${midtrans.api.key}") String secretKey,
            @Value("${midtrans.api.snap-url}") String baseUrlSnap
    ) {
        this.paymentRepository = paymentRepository;
        this.restClient = restClient;
        SECRET_KEY = secretKey;
        BASE_URL_SNAP = baseUrlSnap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Payment createPayment(Transaction transaction) {
        long amount = transaction.getTransactionDetails()
                .stream().mapToLong(value -> (value.getQty() * value.getProductPrice()))
                .reduce(0, Long::sum);

        List<PaymentItemDetailRequest> itemDetailRequestList = transaction.getTransactionDetails().stream()
                .map(transactionDetail -> PaymentItemDetailRequest.builder()
                        .name(transactionDetail.getProduct().getName())
                        .price(transactionDetail.getProductPrice())
                        .quantity(transactionDetail.getQty())
                        .build()).toList();

        PaymentRequest request = PaymentRequest.builder()
                .paymentDetail(PaymentDetailRequest.builder()
                        .orderId(transaction.getId())
                        .amount(amount)
                        .build())
                .customer(PaymentCustomerRequest.builder()
                        .name(transaction.getCustomer() != null ? transaction.getCustomer().getName() : "Guest")
                        .build())
                .paymentItemDetails(itemDetailRequestList)
                .paymentMethod(List.of("shopeepay", "gopay"))
                .build();

        ResponseEntity<Map<String, String>> response = restClient.post()
                .uri(BASE_URL_SNAP)
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + SECRET_KEY)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

        Map<String, String> body = response.getBody();

        if (body == null) return null;

        Payment payment = Payment.builder()
                .token(body.get("token"))
                .redirectUrl(body.get("redirect_url"))
                .transactionStatus(TransactionStatus.ORDERED)
                .build();
        paymentRepository.saveAndFlush(payment);

        return payment;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkFailedAndUpdatePayments() {
        List<TransactionStatus> transactionStatus = List.of(
                TransactionStatus.DENY,
                TransactionStatus.CANCEL,
                TransactionStatus.EXPIRE,
                TransactionStatus.FAILURE
        );
        List<Payment> payments = paymentRepository.findAllByTransactionStatusIn(transactionStatus);

        for (Payment payment : payments) {
            for (TransactionDetail transactionDetail : payment.getTransaction().getTransactionDetails()) {
                Product product = transactionDetail.getProduct();
                product.setStock(product.getStock() + transactionDetail.getQty());
            }
            payment.setTransactionStatus(TransactionStatus.FAILURE);
        }
    }
}

