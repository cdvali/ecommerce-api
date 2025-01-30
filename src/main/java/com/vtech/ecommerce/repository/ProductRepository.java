package com.vtech.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtech.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
