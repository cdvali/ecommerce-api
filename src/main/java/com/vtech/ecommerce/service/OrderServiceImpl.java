package com.vtech.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vtech.ecommerce.audit.service.AuditLogService;
import com.vtech.ecommerce.dto.OrderDTO;
import com.vtech.ecommerce.dto.OrderProductDTO;
import com.vtech.ecommerce.exception.ResourceNotFoundException;
import com.vtech.ecommerce.mapper.OrderMapper;
import com.vtech.ecommerce.model.Order;
import com.vtech.ecommerce.model.OrderProduct;
import com.vtech.ecommerce.model.OrderStatus;
import com.vtech.ecommerce.model.Product;
import com.vtech.ecommerce.repository.OrderProductRepository;
import com.vtech.ecommerce.repository.OrderRepository;
import com.vtech.ecommerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	private final OrderProductRepository orderProductRepository;
	private final ProductRepository productRepository;
	private final AuditLogService auditLogService;

	@Override
	public Iterable<OrderDTO> getAllOrders() {
		return this.orderRepository.findAll().stream().map(orderMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public OrderDTO getOrderById(Long id) {
		return this.orderRepository.findById(id).map(orderMapper::toDTO)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
	}
	
	@Override
	public OrderDTO create(OrderDTO orderDTO) {
		Order order = orderMapper.toEntity(orderDTO);
		order.setStatus(OrderStatus.PENDING.name());
		Order savedOrder = orderRepository.save(order);

		List<OrderProduct> orderProducts = new ArrayList<>();
		for (OrderProductDTO orderProductDTO : orderDTO.getOrderProducts()) {
			Product product = productRepository.findById(orderProductDTO.getProductId())
					.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
			OrderProduct orderProduct = new OrderProduct(savedOrder, product, orderProductDTO.getQuantity());
			OrderProduct savedOrderProduct = orderProductRepository.save(orderProduct);
			orderProducts.add(savedOrderProduct);
		}

		savedOrder.setOrderProducts(orderProducts);
		savedOrder = orderRepository.save(savedOrder);
		
		auditLogService.logAction("update", "order", savedOrder.toString());

		return orderMapper.toDTO(savedOrder);
	}
	
	@Override
	public void update(Long orderId, OrderDTO orderDTO) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		order.setStatus(OrderStatus.PENDING.name());
		Order savedOrder = orderRepository.save(order);

		orderProductRepository.deleteByPkOrderId(orderId);
		
		List<OrderProduct> orderProducts = new ArrayList<>();
		for (OrderProductDTO orderProductDTO : orderDTO.getOrderProducts()) {
			Product product = productRepository.findById(orderProductDTO.getProductId())
					.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
			OrderProduct orderProduct = new OrderProduct(savedOrder, product, orderProductDTO.getQuantity());
			OrderProduct savedOrderProduct = orderProductRepository.save(orderProduct);
			orderProducts.add(savedOrderProduct);
		}

		savedOrder.setOrderProducts(orderProducts);
		Order updatedOrder = orderRepository.save(savedOrder);
		
		auditLogService.logAction("update", "order", updatedOrder.toString());
	}
	
	@Override
	public void deleteById(Long id) {
		orderProductRepository.deleteByPkOrderId(id);
		orderRepository.deleteById(id);
		
		auditLogService.logAction("delete", "order", id.toString());
	}
}
