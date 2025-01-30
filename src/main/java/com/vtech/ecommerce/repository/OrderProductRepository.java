package com.vtech.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtech.ecommerce.model.OrderProduct;
import com.vtech.ecommerce.model.OrderProductPK;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {
	void deleteByPkOrderId(Long orderId);
}
