package com.example.demo.controllers;
import com.example.demo.services.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final CloudinaryService cloudinaryService;


    public ImageController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }
    @PostMapping("/upload/product/{id}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable String id) throws IOException {
        return ResponseEntity.ok(this.cloudinaryService.upload(file, Long.valueOf(id)));

    }
}