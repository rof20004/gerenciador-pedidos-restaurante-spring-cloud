package br.com.digitalinnovationone.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.digitalinnovationone.order.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
