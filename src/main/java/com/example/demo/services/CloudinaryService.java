package com.example.demo.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.models.Image;
import com.example.demo.models.Product;
import com.example.demo.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;


    public CloudinaryService(ImageRepository imageRepository, @Value("${CLOUDINARY_NAME}")
                             String cloudName,
                             @Value("${CLOUDINARY_API_KEY}")
                             String apiKey,
                             @Value("${CLOUDINARY_API_SECRET}")
                             String apiSecret
    ) {
        this.imageRepository = imageRepository;
        this.cloudinary = new Cloudinary(Map.of(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    public String upload(MultipartFile file, Long productId) throws IOException {
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource _type", "auto"));

        Image image = new Image();
        image.setUrl(uploadResult.get("url").toString());
        Product product = new Product();
        product.setId(productId);
        image.setProduct(product);

        imageRepository.save(image);
        return "Imagen guardada con éxito";
    }

    public String upload(MultipartFile file) throws IOException {
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource _type", "auto"));

        return uploadResult.get("url").toString();

    }


}
