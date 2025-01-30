package com.vtech.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vtech.ecommerce.dto.OrderDTO;
import com.vtech.ecommerce.model.Order;

@Mapper(componentModel = "spring", uses = OrderProductMapper.class)
public interface OrderMapper {

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "totalOrderPrice", expression = "java(order.getTotalOrderPrice())")
    @Mapping(target = "numberOfProducts", expression = "java(order.getNumberOfProducts())")
    OrderDTO toDTO(Order order);

    @Mapping(target = "orderProducts", ignore = true)
    Order toEntity(OrderDTO orderDTO);
}