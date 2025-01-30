package com.vtech.ecommerce.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vtech.ecommerce.dto.OrderDTO;
import com.vtech.ecommerce.service.OrderService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @NotNull Iterable<OrderDTO> list() {
        return this.orderService.getAllOrders();
    }

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") Long id) {
		OrderDTO orderDTO = orderService.getOrderById(id);
		return ResponseEntity.ok(orderDTO);
	}
	
    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        OrderDTO savedOrderDTO = this.orderService.create(orderDTO);

        String uri = ServletUriComponentsBuilder
          .fromCurrentServletMapping()
          .path("/orders/{id}")
          .buildAndExpand(savedOrderDTO.getId())
          .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(savedOrderDTO, headers, HttpStatus.CREATED);
    }
    
	@PutMapping("/{id}")
	public ResponseEntity<OrderDTO> update(@PathVariable("id") Long id, @RequestBody OrderDTO orderDTO) {
		this.orderService.update(id, orderDTO);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<OrderDTO> delete(@PathVariable("id") Long id) {
		this.orderService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}