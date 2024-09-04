package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.constant.ResponseMessage;
import com.enigmacamp.tokonyadia.model.dto.request.SearchAdminRequest;
import com.enigmacamp.tokonyadia.model.dto.request.UpdateAdminRequest;
import com.enigmacamp.tokonyadia.model.dto.response.AdminResponse;
import com.enigmacamp.tokonyadia.model.dto.response.ImageResponse;
import com.enigmacamp.tokonyadia.model.entity.Admin;
import com.enigmacamp.tokonyadia.model.entity.Image;
import com.enigmacamp.tokonyadia.model.entity.UserAccount;
import com.enigmacamp.tokonyadia.repository.AdminRepository;
import com.enigmacamp.tokonyadia.service.AdminService;
import com.enigmacamp.tokonyadia.service.ImageService;
import com.enigmacamp.tokonyadia.utils.specifications.AdminSpecification;
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

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Admin create(Admin admin) {
        return adminRepository.saveAndFlush(admin);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminResponse getOne(String id) {
        Admin admin = findByIdOrThrowNotFound(id);
        return convertAdminToAdminResponse(admin);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminResponse> getAll(SearchAdminRequest request) {
        Specification<Admin> specification = AdminSpecification.getSpecification(request.getQuery());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return adminRepository.findAll(specification, pageable).map(this::convertAdminToAdminResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AdminResponse update(UpdateAdminRequest request) {
        Admin admin = findByIdOrThrowNotFound(request.getId());
        admin.setName(request.getName());
        admin.setAddress(request.getAddress());
        admin.setPosition(request.getPosition());
        admin.setEmail(request.getEmail());

        if (StringUtils.hasText(request.getPassword())) {
            UserAccount userAccount = admin.getUserAccount();
            userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            if (admin.getImage() != null) {
                adminRepository.delete(admin);
                imageService.deleteById(admin.getImage().getId());
            }
            Image image = imageService.create(request.getImage());
            admin.setImage(image);
        }

        adminRepository.saveAndFlush(admin);

        return convertAdminToAdminResponse(admin);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusById(String id, Boolean status) {
        findByIdOrThrowNotFound(id);
        adminRepository.updateStatus(id, status);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Admin admin = findByIdOrThrowNotFound(id);
        if (admin.getImage() != null) {
            imageService.deleteById(admin.getImage().getId());
        }
        adminRepository.delete(admin);
    }

    private Admin findByIdOrThrowNotFound(String request) {
        return adminRepository.findById(request)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    private AdminResponse convertAdminToAdminResponse(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .address(admin.getAddress())
                .position(admin.getPosition())
                .email(admin.getEmail())
                .status(admin.getStatus())
                .image(ImageResponse.builder()
                        .url(APIUrl.ADMIN_IMAGE_DOWNLOAD_API + "/" + admin.getImage().getId())
                        .name(admin.getImage().getName())
                        .build())
                .build();
    }
}
