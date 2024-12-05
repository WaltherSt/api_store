package com.example.demo.dtos.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long category_id;
    private List<String> images = new ArrayList<>();
    private List<ProductCommentDTO> comments= new ArrayList<>();

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(String description, Long id, String name, BigDecimal price, Long category_id,
                              Integer stock,
                              List<String> images, List<ProductCommentDTO> comments) {
        this.description = description;
        this.id = id;
        this.name = name;
        this.price = price;
        this.category_id = category_id;
        this.stock = stock;
        this.images = images;
        this.comments = comments;
    }

    public ProductResponseDTO(String name, Long id, String description, Long category_id, Integer stock, BigDecimal price,
                              List<String> images, List<ProductCommentDTO> comments) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.category_id = category_id;
        this.stock = stock;
        this.price = price;
        this.images = images;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<ProductCommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<ProductCommentDTO> comments) {
        this.comments = comments;
    }
}
