package com.vtech.ecommerce.service;

import java.io.InputStream;

import org.springframework.validation.annotation.Validated;

import com.vtech.ecommerce.dto.ProductDTO;
import com.vtech.ecommerce.model.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Validated
public interface ProductService {

	@NotNull
	Iterable<ProductDTO> getAllProducts();

	ProductDTO getProductById(@NotNull @Min(value = 1L, message = "Invalid product ID.") Long id);

	ProductDTO create(ProductDTO productDTO);

	void update(Long productId, ProductDTO productDTO);

	void deleteAll();

	void deleteById(Long id);

	ProductDTO uploadPhoto(Long productId, String fileName, InputStream fileStream, long fileSize);

	InputStream downloadPhoto(Long productId);

}
