package com.example.demo.services;

import com.example.demo.models.Order;
import com.example.demo.models.OrderDetail;
import com.example.demo.models.Product;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;

        this.productRepository = productRepository;

    }

    @Override
    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();

    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return this.orderRepository.findById(id);
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        // Crear una nueva instancia de Order
        Order currentOrder = new Order();

        // Asignar los datos básicos de la orden
        currentOrder.setCustomer(order.getCustomer());
        currentOrder.setOrderAmount(BigDecimal.ZERO); // Inicializamos el monto total en cero temporalmente

        // Guardar la orden en la base de datos primero (sin los detalles aún)
        currentOrder = orderRepository.save(currentOrder);

        List<OrderDetail> orderDetails = new ArrayList<>();

        // Iterar sobre los detalles de la orden recibidos
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            // Buscar el producto por su ID
            Product product = productRepository.findById(orderDetail.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Calcular el precio total (precio * cantidad)
            BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity()));

            // Crear un nuevo OrderDetail, asignar el producto, el precio calculado y la referencia a la orden
            OrderDetail newOrderDetail = new OrderDetail();
            newOrderDetail.setProduct(product);
            newOrderDetail.setQuantity(orderDetail.getQuantity());
            newOrderDetail.setPrice(totalPrice);
            newOrderDetail.setOrder(currentOrder); // Vinculamos este detalle con la orden guardada

            // Agregar el OrderDetail a la lista y a la orden
            orderDetails.add(newOrderDetail);
        }

        // Actualizar el monto total de la orden
        BigDecimal totalAmount = orderDetails.stream()
                .map(OrderDetail::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        currentOrder.setOrderAmount(totalAmount);

        // Guardar nuevamente la orden con los detalles vinculados
        currentOrder.setOrderDetails(orderDetails);

        return orderRepository.save(currentOrder);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        order.setId(id);
        return this.orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        this.orderRepository.deleteById(id);

    }
}
