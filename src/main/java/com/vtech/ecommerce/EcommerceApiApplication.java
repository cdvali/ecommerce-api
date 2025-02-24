package com.vtech.ecommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vtech.ecommerce.dto.ProductDTO;
import com.vtech.ecommerce.service.ProductService;

@SpringBootApplication
public class EcommerceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApiApplication.class, args);
	}

	@Bean
    CommandLineRunner runner(ProductService productService) {
        return args -> {
//        	productService.deleteAll();
//            productService.create(new ProductDTO("Samsung S24", 5299.00));
//            productService.create(new ProductDTO("iPhone 13", 2599.00));
//            productService.create(new ProductDTO("Samsung A12", 731.00));
//            productService.create(new ProductDTO("Nokia 150", 199.00));
//            productService.create(new ProductDTO("Motorola Moto G54", 999.00));
//            productService.create(new ProductDTO("Xiaomi Redmi Note 14", 1699.00));
//            productService.create(new ProductDTO("iPhone 16 Pro", 6999.00));
//            productService.create(new ProductDTO("OnePlus 13", 5932.00));
//            productService.create(new ProductDTO("OnePlus Nord 3", 1499.00));
//            productService.create(new ProductDTO("Oppo Reno 12F", 1399.00));
        };
    }
}
