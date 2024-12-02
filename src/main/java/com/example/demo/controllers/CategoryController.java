package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.services.interfaces.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());  // Retorna 404 si no se encuentra la categoría
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(201).body(createdCategory);  // Retorna un 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);  // Retorna la categoría actualizada con un estado 200 OK
        } else {
            return ResponseEntity.notFound().build();  // Retorna un 404 Not Found si no se encuentra la categoría
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);  // Intenta eliminar la categoría
            return ResponseEntity.noContent().build();  // Retorna 204 No Content si la eliminación fue exitosa
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();  // Retorna 404 Not Found si no se encuentra la categoría
        }
    }
}
