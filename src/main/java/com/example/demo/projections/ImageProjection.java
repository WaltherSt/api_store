package com.example.demo.projections;

public interface ImageProjection {
    Long getImageId(); // Este debe coincidir con el alias "imageId" de la consulta
    String getImageUrl(); // Este debe coincidir con el alias "imageUrl" de la consulta
}