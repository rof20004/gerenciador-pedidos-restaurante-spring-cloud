package br.com.digitalinnovationone.customer.controller;

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

import br.com.digitalinnovationone.customer.exception.ResourceNotFoundException;
import br.com.digitalinnovationone.customer.model.Customer;
import br.com.digitalinnovationone.customer.repository.CustomerRepository;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "customerId") Long customerId) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId).orElseThrow(customerNotFoundSupplier(customerId));
		return ResponseEntity.ok().body(customer);
	}
	
	@PostMapping("/customers")
	public Customer createCustomer(@Valid @RequestBody Customer requestBody) {
		return customerRepository.save(requestBody);
	}
	
	@PutMapping("/customers/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "customerId") Long customerId, @Valid @RequestBody Customer requestBody) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId).orElseThrow(customerNotFoundSupplier(customerId));
		customer.setName(requestBody.getName());
		customer.setAddress(requestBody.getAddress());
		
		final Customer updatedCustomer = customerRepository.save(customer);
		
		return ResponseEntity.ok().body(updatedCustomer);
	}
	
	@DeleteMapping("/customer/{customerId}")
	public void deleteCustomer(@PathVariable(value = "customerId") Long customerId) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId).orElseThrow(customerNotFoundSupplier(customerId));
		customerRepository.delete(customer);
	}
	
	private final Supplier<ResourceNotFoundException> customerNotFoundSupplier(Long customerId) {
		ResourceNotFoundException ex = new ResourceNotFoundException("Customer not found for this id :: " + customerId);
		return () -> ex;
	}
	
}
