package com.example.demo.models;

import com.example.demo.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal orderAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference(value = "customer-order") // Referencia única para la relación con Customer
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference(value="order-detail")  // Referencia única para la relación con OrderDetail
    private List<OrderDetail> orderDetails;

    @PrePersist
    private void setOrderDate() {
            this.orderDate = LocalDateTime.now();
    }


    public Order() {
    }

    public Order(Customer customer, Long id, BigDecimal orderAmount, LocalDateTime orderDate,
                 List<OrderDetail> orderDetails, OrderStatus status) {
        this.customer = customer;
        this.id = id;
        this.orderAmount = orderAmount;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer.getId() +
                ", id=" + id +
                ", orderDate=" + orderDate +
                ", orderAmount=" + orderAmount +
                ", status=" + status +
                ", orderDetails=" + orderDetails.size() +
                '}';
    }
}