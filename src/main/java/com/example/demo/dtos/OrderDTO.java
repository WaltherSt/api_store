package com.example.demo.dtos;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal orderAmount;
    private List<OrderDetailDTO> orderDetails;

    public OrderDTO() {
    }

    public OrderDTO(Long id, BigDecimal orderAmount, LocalDateTime orderDate, List<OrderDetailDTO> orderDetails) {
        this.id = id;
        this.orderAmount = orderAmount;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
