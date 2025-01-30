package com.vtech.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtech.ecommerce.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
