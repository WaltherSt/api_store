package com.example.demo.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductRequest {
    private String name;
    private String description;
    private int price;
    private int stock;
    private Long category;
    private List<MultipartFile> images;

    public ProductRequest() {
    }

    public ProductRequest(Long category, String description, List<MultipartFile> images, String name, int price,
                          int stock) {
        this.category = category;
        this.description = description;
        this.images = images;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
