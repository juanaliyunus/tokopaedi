package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.constant.UserRole;
import com.enigmacamp.tokonyadia.model.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
