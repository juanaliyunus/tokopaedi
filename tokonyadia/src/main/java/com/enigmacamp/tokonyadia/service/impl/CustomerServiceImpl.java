package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.constant.ResponseMessage;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.request.SearchCustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.request.UpdateCustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.dto.response.ImageResponse;
import com.enigmacamp.tokonyadia.model.entity.Customer;
import com.enigmacamp.tokonyadia.model.entity.Image;
import com.enigmacamp.tokonyadia.model.entity.UserAccount;
import com.enigmacamp.tokonyadia.repository.CustomerRepository;
import com.enigmacamp.tokonyadia.service.CustomerService;
import com.enigmacamp.tokonyadia.service.ImageService;
import com.enigmacamp.tokonyadia.service.UserService;
import com.enigmacamp.tokonyadia.utils.exeptions.ResourceNotFoundExeption;
import com.enigmacamp.tokonyadia.utils.specifications.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserService userService;
    private final CustomerRepository customerRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponse getOneById(String id) {
        return convertCustomerToCustomerResponse(findByIdOrThrowNotFound(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponse> getAll(SearchCustomerRequest request) {
        Specification<Customer> specification = CustomerSpecification.getSpecification(request.getQuery());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return customerRepository.findAll(specification, pageable).map(this::convertCustomerToCustomerResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse update(UpdateCustomerRequest request) {
        Customer currentCustomer = findByIdOrThrowNotFound(request.getId());
        currentCustomer.setName(request.getName());
        currentCustomer.setMobilePhoneNo(request.getMobilePhoneNo());
        currentCustomer.setAddress(request.getAddress());
        currentCustomer.setBirthDate(Date.valueOf(request.getBirthDate()));

        if (StringUtils.hasText(request.getPassword())) {
            UserAccount userAccount = currentCustomer.getUserAccount();
            userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            if (currentCustomer.getImage() != null) {
                customerRepository.delete(currentCustomer);
                imageService.deleteById(currentCustomer.getImage().getId());
            }
            Image image = imageService.create(request.getImage());
            currentCustomer.setImage(image);
        }

        customerRepository.saveAndFlush(currentCustomer);
        return convertCustomerToCustomerResponse(currentCustomer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Customer customer = findByIdOrThrowNotFound(id);
        if (customer.getImage() != null) {
            imageService.deleteById(customer.getImage().getId());
        }
        customerRepository.delete(customer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusById(String id, Boolean status) {
        findByIdOrThrowNotFound(id);
        customerRepository.updateStatus(id, status);
    }

    @Override
    public Customer getByToken() {
        UserAccount userAccount = userService.getByContext();
        return customerRepository.findByUserAccount_Id(userAccount.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    private Customer findByIdOrThrowNotFound(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    private CustomerResponse convertCustomerToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .mobilePhoneNo(customer.getMobilePhoneNo())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .status(customer.getStatus())
                .image(ImageResponse.builder()
                        .url(APIUrl.CUSTOMER_IMAGE_DOWNLOAD_API + "/" + customer.getImage().getId())
                        .name(customer.getImage().getName())
                        .build())
                .userAccountId(customer.getUserAccount().getId())
                .build();
    }
}

