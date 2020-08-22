package br.com.digitalinnovationone.delivery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "customer_id", nullable = false)
	private Long customerId;
	
	@Column(name = "order_id", nullable = false)
	private Long orderId;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "shipping_value", nullable = false)
	private Long shippingValue;
	
}
