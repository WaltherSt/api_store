package com.example.demo.services.interfaces;

import com.example.demo.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {

    List<Image> getAllImages();

    Optional<Image> getImageById(Long id);

    void deleteImage(Long id);

    String saveImage(MultipartFile file, Long productId);
}
