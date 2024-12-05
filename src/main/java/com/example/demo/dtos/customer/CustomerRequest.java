package com.example.demo.dtos.customer;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class CustomerRequest {

    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String password;
    private MultipartFile image;

    public CustomerRequest() {
    }

    public CustomerRequest(String name, String email, String phone, String address, String city, MultipartFile image,
                           String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.image = image;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

}
