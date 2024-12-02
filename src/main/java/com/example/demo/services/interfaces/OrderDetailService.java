package com.example.demo.services.interfaces;

import com.example.demo.dtos.OrderDetailDTO;
import com.example.demo.models.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    List<OrderDetailDTO> getAllOrderDetails();
    Optional<OrderDetail> getOrderDetailById(Long id);
    OrderDetail saveOrderDetail(OrderDetail orderDetail);
    OrderDetail updateOrderDetail(Long id, OrderDetail orderDetail);
    void deleteOrderDetail(Long id);
}


