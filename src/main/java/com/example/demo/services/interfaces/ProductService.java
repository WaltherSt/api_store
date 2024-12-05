package com.example.demo.services.interfaces;

import com.example.demo.dtos.ProductRequest;
import com.example.demo.dtos.product.ProductResponseDTO;
import com.example.demo.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {


    List<ProductResponseDTO> getAllProducts();

    ResponseEntity<ProductResponseDTO> getProductById(Long id);

    Product saveProduct(ProductRequest productRequest);

    Optional<Product> updateProduct(Long id, Product product);

    void deleteProduct(Long id);
}
