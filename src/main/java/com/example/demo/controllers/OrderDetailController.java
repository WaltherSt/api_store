package com.example.demo.controllers;

import com.example.demo.dtos.OrderDetailDTO;
import com.example.demo.models.OrderDetail;
import com.example.demo.services.interfaces.OrderDetailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orderDetails")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    // Constructor para inyectar el servicio
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails();
        return ResponseEntity.ok(orderDetails);
    }

    // Obtener un detalle de pedido por su ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Long id) {
        Optional<OrderDetail> orderDetail = orderDetailService.getOrderDetailById(id);
        return orderDetail.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());  // Retorna 404 si no se encuentra el detalle de pedido
    }

    // Crear un nuevo detalle de pedido
    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        OrderDetail createdOrderDetail = orderDetailService.saveOrderDetail(orderDetail);
        return ResponseEntity.status(201).body(createdOrderDetail);  // Retorna un 201 Created
    }

    // Actualizar un detalle de pedido existente
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetail orderDetail) {
        OrderDetail updatedOrderDetail = orderDetailService.updateOrderDetail(id, orderDetail);
        if (updatedOrderDetail != null) {
            return ResponseEntity.ok(updatedOrderDetail);  // Retorna el detalle de pedido actualizado con un estado 200 OK
        } else {
            return ResponseEntity.notFound().build();  // Retorna un 404 Not Found si no se encuentra el detalle de pedido
        }
    }

    // Eliminar un detalle de pedido por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        try {
            orderDetailService.deleteOrderDetail(id);  // Intenta eliminar el detalle de pedido
            return ResponseEntity.noContent().build();  // Retorna 204 No Content si la eliminación fue exitosa
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();  // Retorna 404 Not Found si no se encuentra el detalle de pedido
        }
    }
}
