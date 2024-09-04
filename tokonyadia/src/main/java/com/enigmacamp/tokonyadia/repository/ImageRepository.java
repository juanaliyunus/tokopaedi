package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
