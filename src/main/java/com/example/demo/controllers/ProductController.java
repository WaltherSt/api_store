package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.projections.ProductoProjection;
import com.example.demo.services.interfaces.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());  // Retorna 404 si no se encuentra el producto
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.saveProduct(product);
        return ResponseEntity.status(201).body(createdProduct);  // Retorna un 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody Product productDetails) {
        Optional<Product> updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct.orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);  // Intenta eliminar el producto
            return ResponseEntity.noContent().build();  // Retorna 204 No Content si la eliminación fue exitosa
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();  // Retorna 404 Not Found si no se encuentra el producto
        }
    }
}

