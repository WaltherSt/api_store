package com.example.demo.mappers.product;

import com.example.demo.dtos.product.ProductCommentDTO;
import com.example.demo.dtos.product.ProductResponseDTO;
import com.example.demo.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMappers {


    public  ProductResponseDTO productToProductResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        //informacion producto
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setStock(product.getStock());
        productResponseDTO.setCategory_id(product.getCategory().getId());

        //imagenes asociadas
        product.getImages().forEach(image -> {
            productResponseDTO.getImages().add(image.getUrl());
        });

        return productResponseDTO;
    }

    public  ProductResponseDTO productToProductResponseDtoWithComments(Product product){
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setStock(product.getStock());
        productResponseDTO.setCategory_id(product.getCategory().getId());

        //imagenes asociadas
        product.getImages().forEach(image -> {
            productResponseDTO.getImages().add(image.getUrl());
        });

        // comentarios asociados
        product.getComments().forEach(comment -> {
            ProductCommentDTO productCommentDTO = new ProductCommentDTO();
            productCommentDTO.setId(comment.getId());
            productCommentDTO.setComment(comment.getComment());
            productCommentDTO.setCity(comment.getCustomer().getCity());
            productCommentDTO.setDate(comment.getDate());
            productCommentDTO.setCustomerName(comment.getCustomer().getName());
            productCommentDTO.setCustomerImage(comment.getCustomer().getImage());

            productResponseDTO.getComments().add(productCommentDTO);

        });
        return productResponseDTO;
    }
}
