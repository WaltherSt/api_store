package com.example.demo.mappers;

import com.example.demo.dtos.OrderDTO;
import com.example.demo.dtos.OrderDetailDTO;
import com.example.demo.dtos.ProductBasicDTO;
import com.example.demo.models.Order;
import com.example.demo.models.OrderDetail;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface OrderMapper {


    OrderDTO toOrderDTO(Order order);

    OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail);

    ProductBasicDTO toProductBasicDTO(ProductBasicDTO product);

}