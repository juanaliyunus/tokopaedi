package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.constant.ResponseMessage;
import com.enigmacamp.tokonyadia.model.entity.Image;
import com.enigmacamp.tokonyadia.repository.ImageRepository;
import com.enigmacamp.tokonyadia.service.ImageService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private final Path directoryPath;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(@Value("${app.tokonyadiashop.multipart.path-location}") String directoryPath,
                            ImageRepository imageRepository) {
        this.directoryPath = Paths.get(directoryPath);
        this.imageRepository = imageRepository;
    }

    @PostConstruct
    public void initDirectory() {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }

    @Override
    public Image create(MultipartFile multipartFile) {
        try {
            if (!List.of("image/jpeg", "image/png", "image/jpg", "image/svg+xml").contains(multipartFile.getContentType()))
                throw new ConstraintViolationException(ResponseMessage.ERROR_INVALID_CONTENT_TYPE, null);
            String uniqueFilename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            Path filePath = directoryPath.resolve(uniqueFilename);
            Files.copy(multipartFile.getInputStream(), filePath);

            Image image = Image.builder()
                    .name(uniqueFilename)
                    .contentType(multipartFile.getContentType())
                    .size(multipartFile.getSize())
                    .path(filePath.toString())
                    .build();
            imageRepository.saveAndFlush(image);

            return image;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Resource getById(String id) {
        try {
            Image image = imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
            Path filePath = Paths.get(image.getPath());
            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            Image image = imageRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
            Path filePath = Paths.get(image.getPath());
            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            Files.delete(filePath);
            imageRepository.delete(image);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
