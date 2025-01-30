package com.vtech.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vtech.ecommerce.dto.ProductDTO;
import com.vtech.ecommerce.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "createdBy", source = "createdBy")
    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);
}
