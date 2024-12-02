package com.example.demo.controllers;


import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.sas.SasProtocol;
import com.example.demo.services.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageServiceImpl imageService;
    @Value("${AZURE_STORAGE_BLOB_CONNECTION_STRING}")
    private String connectionString;
    @Value("${AZURE_STORAGE_BLOB_CONTAINER_NAME}")
    private String containerName;
    @Value("${AZURE_STORAGE_ACCOUNT_NAME}")
    private String accountName;

    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;


    }

    @GetMapping
    public List<String> listImageUrls() {
        List<String> imageUrls = new ArrayList<>();

        // cliente del contenedor
        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();

        // Iterar sobre los blobs en el contenedor
        for (BlobItem blobItem : containerClient.listBlobs()) {
            // Genera los valores de SAS
            BlobServiceSasSignatureValues sasValues = new BlobServiceSasSignatureValues(
                    OffsetDateTime.now().plusHours(1), // Tiempo de expiración
                    BlobSasPermission.parse("r") // Permiso de lectura
            ).setProtocol(SasProtocol.HTTPS_ONLY);

            // Genera la URL SAS
            String sasUrl = containerClient.getBlobClient(blobItem.getName()).generateSas(sasValues);

            // Construye la URL completa del blob
            String blobUrl = String.format("https://%s.blob.core.windows.net/%s/%s?%s",
                    accountName, containerName, blobItem.getName(), sasUrl);
            imageUrls.add(blobUrl);
        }

        return imageUrls;
    }

    @PostMapping("/upload/product/{id}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable String id) {
        return ResponseEntity.ok(this.imageService.saveImage(file, Long.valueOf(id)));

    }
}