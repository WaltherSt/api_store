package com.example.demo.controllers;

import com.example.demo.dtos.ProductRequest;
import com.example.demo.dtos.product.ProductResponseDTO;
import com.example.demo.models.Product;
import com.example.demo.services.interfaces.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
    return this.productService.getProductById(id);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Map<String,String>> createProduct(@ModelAttribute ProductRequest productRequest) {

        Map<String, String> res = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        try {
            this.productService.saveProduct(productRequest);
            res.put("message", "Product created");
            return ResponseEntity.status(HttpStatus.CREATED).body(res);

        } catch (Exception e) {
            errors.put("message", e.getMessage());
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
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

