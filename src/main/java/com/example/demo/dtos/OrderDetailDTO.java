package com.example.demo.dtos;


import com.example.demo.models.Product;

import java.math.BigDecimal;

public class OrderDetailDTO {
    private Long id;
    private Integer quantity;
    private BigDecimal price;  // El precio en el momento de la compra
    private ProductBasicDTO product;  // Solo con datos básicos del producto

    // Getters and Setters


    public OrderDetailDTO() {
    }

    public OrderDetailDTO(Long id, BigDecimal price, ProductBasicDTO product, Integer quantity) {
        this.id = id;
        this.price = price;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductBasicDTO getProduct() {
        return product;
    }

    public void setProduct(ProductBasicDTO product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}