package com.vtech.ecommerce.service;

import java.io.InputStream;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vtech.ecommerce.dto.ProductDTO;
import com.vtech.ecommerce.exception.ResourceNotFoundException;
import com.vtech.ecommerce.mapper.ProductMapper;
import com.vtech.ecommerce.model.Product;
import com.vtech.ecommerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final S3Service s3Service;

	@Override
	public Iterable<ProductDTO> getAllProducts() {
		return productRepository.findAll().stream().map(productMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public ProductDTO getProductById(Long id) {
		return productRepository.findById(id).map(productMapper::toDTO)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	}

	@Override
	public ProductDTO create(ProductDTO productDTO) {
		Product product = productMapper.toEntity(productDTO);
		Product savedProduct = productRepository.save(product);
		return productMapper.toDTO(savedProduct);
	}

	@Override
	public void update(Long productId, ProductDTO productDTO) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		productRepository.save(product);
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public ProductDTO uploadPhoto(Long productId, String fileName, InputStream fileStream, long fileSize) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		String photoPath = s3Service.uploadFile(fileName, fileStream, fileSize);

		product.setPhotoPath(photoPath);

		Product savedProduct = productRepository.save(product);
		return productMapper.toDTO(savedProduct);
	}

	@Override
	public InputStream downloadPhoto(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		String photoPath = product.getPhotoPath();

		if (photoPath == null || photoPath.isEmpty()) {
			throw new ResourceNotFoundException("Product photo not available");
		}

		return s3Service.downloadFile(photoPath);
	}
}
