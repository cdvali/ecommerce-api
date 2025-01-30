package com.vtech.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vtech.ecommerce.dto.OrderProductDTO;
import com.vtech.ecommerce.model.OrderProduct;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {
        
    @Mapping(source = "pk.product.id", target = "productId")
    @Mapping(source = "pk.product.name", target = "productName")
    OrderProductDTO toDTO(OrderProduct orderProduct);

    @Mapping(target = "pk.product.id", source = "productId")
    @Mapping(target = "pk.order", ignore = true)
    @Mapping(target = "pk.product", ignore = true)
    OrderProduct toEntity(OrderProductDTO orderProductDTO);
}
