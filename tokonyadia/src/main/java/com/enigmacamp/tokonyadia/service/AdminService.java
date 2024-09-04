package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.SearchAdminRequest;
import com.enigmacamp.tokonyadia.model.dto.request.UpdateAdminRequest;
import com.enigmacamp.tokonyadia.model.dto.response.AdminResponse;
import com.enigmacamp.tokonyadia.model.entity.Admin;
import org.springframework.data.domain.Page;

public interface AdminService {
    Admin create(Admin admin);
    AdminResponse getOne(String id);
    Page<AdminResponse> getAll(SearchAdminRequest request);
    AdminResponse update(UpdateAdminRequest request);
    void updateStatusById(String id, Boolean status);

    void deleteById(String id);
}

