package com.example.demo.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.example.demo.models.Image;
import com.example.demo.models.Product;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.services.interfaces.ImageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ImageServiceImpl implements ImageService {



    private final ImageRepository imageRepository;

    private BlobContainerClient containerClient;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }



    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public void deleteImage(Long id) {
        // Implementar lógica para eliminar imagen si es necesario
    }

    @Override
    public String saveImage(MultipartFile file, Long productId) {
       return "";
    }

}


