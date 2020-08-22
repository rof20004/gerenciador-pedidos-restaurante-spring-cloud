package br.com.digitalinnovationone.order.controller;

import java.util.List;
import java.util.function.Supplier;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.digitalinnovationone.order.exception.ResourceNotFoundException;
import br.com.digitalinnovationone.order.model.Order;
import br.com.digitalinnovationone.order.repository.OrderRepository;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping("/orders")
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}
	
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable(value = "orderId") Long orderId) throws ResourceNotFoundException {
		Order order = orderRepository.findById(orderId).orElseThrow(orderNotFoundSupplier(orderId));
		return ResponseEntity.ok().body(order);
	}
	
	@PostMapping("/orders")
	public Order createOrder(@Valid @RequestBody Order requestBody) {
		return orderRepository.save(requestBody);
	}
	
	@PutMapping("/orders/{orderId}")
	public ResponseEntity<Order> updateOrder(@PathVariable(value = "orderId") Long orderId, @Valid @RequestBody Order requestBody) throws ResourceNotFoundException {
		Order order = orderRepository.findById(orderId).orElseThrow(orderNotFoundSupplier(orderId));
		order.setCustomerId(requestBody.getCustomerId());
		order.setDescription(requestBody.getDescription());
		order.setValue(requestBody.getValue());;
		
		final Order updatedOrder = orderRepository.save(order);
		
		return ResponseEntity.ok().body(updatedOrder);
	}
	
	@DeleteMapping("/order/{orderId}")
	public void deleteOrder(@PathVariable(value = "orderId") Long orderId) throws ResourceNotFoundException {
		Order order = orderRepository.findById(orderId).orElseThrow(orderNotFoundSupplier(orderId));
		orderRepository.delete(order);
	}
	
	private final Supplier<ResourceNotFoundException> orderNotFoundSupplier(Long orderId) {
		ResourceNotFoundException ex = new ResourceNotFoundException("Order not found for this id :: " + orderId);
		return () -> ex;
	}
	
}
