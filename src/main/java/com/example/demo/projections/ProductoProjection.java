package com.example.demo.projections;

import com.example.demo.models.Category;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoProjection {
    Long getId();
    String getName();
    BigDecimal getPrice();
    Integer getStock();
    String getDescription();
    Category getCategory();
    List<ImageProjection> getImages(); // Proyección para imágenes
    List<CommentProjection> getComments(); // Proyección para comentarios
}