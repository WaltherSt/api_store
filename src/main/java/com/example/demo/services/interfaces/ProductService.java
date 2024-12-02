package com.example.demo.services.interfaces;

import com.example.demo.models.Product;
import com.example.demo.projections.ProductoProjection;

import java.util.List;
import java.util.Optional;

public interface ProductService {


    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product saveProduct(Product product);

    Optional<Product> updateProduct(Long id, Product product);

    void deleteProduct(Long id);
}
