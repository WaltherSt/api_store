package com.example.demo.dtos.product;

import java.time.LocalDateTime;

public class ProductCommentDTO {
    private long id;
    private String comment;
    private String customerName;
    private String customerImage;
    private LocalDateTime date;
    private String city;


    public ProductCommentDTO(long id, String comment, String customerName, String customerImage, LocalDateTime date,
                             String city) {
        this.id = id;
        this.comment = comment;
        this.customerName = customerName;
        this.customerImage = customerImage;
        this.date = date;
        this.city = city;
    }
    public ProductCommentDTO() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ProductCommentDTO{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerImage='" + customerImage + '\'' +
                ", date=" + date +
                ", city='" + city + '\'' +
                '}';
    }
}
