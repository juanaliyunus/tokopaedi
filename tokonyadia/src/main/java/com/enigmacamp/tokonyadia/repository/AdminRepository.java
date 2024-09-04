package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AdminRepository extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {
    @Modifying
    @Query(value = "UPDATE m_admin SET status = :status WHERE id = :id", nativeQuery = true)
    void updateStatus(@Param("id") String id, @Param("status") Boolean status);
}
