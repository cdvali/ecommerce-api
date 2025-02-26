package com.vtech.ecommerce.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vtech.ecommerce.dto.ProductDTO;
import com.vtech.ecommerce.service.ProductService;
import com.vtech.ecommerce.service.S3Service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public @NotNull Iterable<ProductDTO> getProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
		ProductDTO productDTO = productService.getProductById(id);
		return ResponseEntity.ok(productDTO);
	}

	@PostMapping
	public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO ProductDTO) {
		ProductDTO savedProductDTO = this.productService.create(ProductDTO);

		String uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/products/{id}")
				.buildAndExpand(savedProductDTO.getId()).toString();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", uri);

		return new ResponseEntity<>(savedProductDTO, headers, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) {
		productService.update(id, productDTO);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ProductDTO> delete(@PathVariable("id") Long id) {
		productService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{id}/upload")
	public ResponseEntity<String> uploadPhoto(@PathVariable("id") Long id,
			@RequestParam("file") MultipartFile file) {

		try {
			ProductDTO productDTO = productService.uploadPhoto(id, file.getOriginalFilename(), file.getInputStream(),
					file.getSize());
			return ResponseEntity.ok(productDTO.getPhotoPath());
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
		}
	}

	@GetMapping("/{id}/download")
	public ResponseEntity<InputStreamResource> downloadPhoto(@PathVariable("id") Long id) {
        InputStream photoStream = productService.downloadPhoto(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=product-photo.jpg")
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(photoStream));
	}
}
