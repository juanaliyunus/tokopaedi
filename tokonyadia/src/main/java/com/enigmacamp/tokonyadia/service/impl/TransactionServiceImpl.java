package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.dto.request.TransactionRequest;
import com.enigmacamp.tokonyadia.model.dto.response.TransactionResponse;
import com.enigmacamp.tokonyadia.model.entity.Customer;
import com.enigmacamp.tokonyadia.model.entity.Product;
import com.enigmacamp.tokonyadia.model.entity.Transaction;
import com.enigmacamp.tokonyadia.model.entity.TransactionDetail;
import com.enigmacamp.tokonyadia.repository.TransactionDetailRepository;
import com.enigmacamp.tokonyadia.repository.TransactionRepository;
import com.enigmacamp.tokonyadia.service.CustomerService;
import com.enigmacamp.tokonyadia.service.ProductService;
import com.enigmacamp.tokonyadia.service.TransactionService;

import com.enigmacamp.tokonyadia.utils.exeptions.ValidationExeption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse create(TransactionRequest transactionRequest) {
        Customer customer = customerService.getById(transactionRequest.getCustomerId());
        Date currentDate = new Date(); // Date on service
        Transaction transaction = Transaction.builder()
                .customer(customer)
                .transDate(currentDate)
                .build();

        AtomicReference<Long> totalPayment = new AtomicReference<>(0L);

        List<TransactionDetail> transactionDetails = transactionRequest.getTransactionDetails().stream().map(detailRequest -> {
            Product product = productService.getProductById(detailRequest.getProductId());
            if(product.getStock() - detailRequest.getQty() < 0){
                throw new ValidationExeption("the product currently out of stock", new RuntimeException("the product currently out of stock"));
            }

            product.setStock(product.getStock() - detailRequest.getQty());

            TransactionDetail trxDetail = TransactionDetail.builder()
                    .product(product)
                    .transaction(transaction)
                    .qty(detailRequest.getQty())
                    .productPrice(product.getPrice())
                    .build();

            totalPayment.updateAndGet(v -> v + product.getPrice() * detailRequest.getQty());

            //TODO: Insert Transaction Detail
            transactionDetailRepository.save(trxDetail);
            return trxDetail;
        }).toList();

        //TODO: Insert Transaction
        transaction.setTransactionDetails(transactionDetails);
        Transaction resultTransaction = transactionRepository.saveAndFlush(transaction);

        //TODO: Create Transaction Response
        return TransactionResponse.builder()
                .id(resultTransaction.getId())
                .customer(resultTransaction.getCustomer())
                .date(resultTransaction.getTransDate())
                .transactionDetails(resultTransaction.getTransactionDetails())
                .totalPayment(totalPayment.get()) // TODO: Create total Payment
                .build();
    }
}
