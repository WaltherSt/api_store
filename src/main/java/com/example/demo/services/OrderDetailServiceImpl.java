package com.example.demo.services;

import com.example.demo.dtos.OrderDetailDTO;
import com.example.demo.models.OrderDetail;
import com.example.demo.models.Product;
import com.example.demo.repositories.OrderDetailRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.interfaces.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl  implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    private final ProductRepository productRepository;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;

        this.productRepository = productRepository;
    }


    @Override
    public List<OrderDetailDTO> getAllOrderDetails() {
        return List.of();
    }

    @Override
    public Optional<OrderDetail> getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {

        Product product = productRepository.findById(orderDetail.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity()));
        orderDetail.setPrice(totalPrice);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetail orderDetail) {
        orderDetail.setId(id);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }
}
