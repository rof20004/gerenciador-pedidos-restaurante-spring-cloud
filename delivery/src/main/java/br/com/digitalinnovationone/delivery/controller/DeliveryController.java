package br.com.digitalinnovationone.delivery.controller;

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

import br.com.digitalinnovationone.delivery.exception.ResourceNotFoundException;
import br.com.digitalinnovationone.delivery.model.Delivery;
import br.com.digitalinnovationone.delivery.repository.DeliveryRepository;

@RestController
@RequestMapping("/api/v1")
public class DeliveryController {
	
	@Autowired
	private DeliveryRepository deliveryRepository;

	@GetMapping("/deliverys")
	public List<Delivery> getAllDeliverys() {
		return deliveryRepository.findAll();
	}
	
	@GetMapping("/deliverys/{deliveryId}")
	public ResponseEntity<Delivery> getDeliveryById(@PathVariable(value = "deliveryId") Long deliveryId) throws ResourceNotFoundException {
		Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(deliveryNotFoundSupplier(deliveryId));
		return ResponseEntity.ok().body(delivery);
	}
	
	@PostMapping("/deliverys")
	public Delivery createDelivery(@Valid @RequestBody Delivery requestBody) {
		return deliveryRepository.save(requestBody);
	}
	
	@PutMapping("/deliverys/{deliveryId}")
	public ResponseEntity<Delivery> updateDelivery(@PathVariable(value = "deliveryId") Long deliveryId, @Valid @RequestBody Delivery requestBody) throws ResourceNotFoundException {
		Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(deliveryNotFoundSupplier(deliveryId));
		delivery.setCustomerId(requestBody.getCustomerId());
		delivery.setOrderId(requestBody.getOrderId());
		delivery.setAddress(requestBody.getAddress());
		delivery.setShippingValue(requestBody.getShippingValue());
		
		final Delivery updatedDelivery = deliveryRepository.save(delivery);
		
		return ResponseEntity.ok().body(updatedDelivery);
	}
	
	@DeleteMapping("/delivery/{deliveryId}")
	public void deleteDelivery(@PathVariable(value = "deliveryId") Long deliveryId) throws ResourceNotFoundException {
		Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(deliveryNotFoundSupplier(deliveryId));
		deliveryRepository.delete(delivery);
	}
	
	private final Supplier<ResourceNotFoundException> deliveryNotFoundSupplier(Long deliveryId) {
		ResourceNotFoundException ex = new ResourceNotFoundException("Delivery not found for this id :: " + deliveryId);
		return () -> ex;
	}
	
}
