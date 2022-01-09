package com.getir.readingisgood.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.getir.readingisgood.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	
	Page<Order> findByCustomerId(String customerId, Pageable pageable);
	
	List<Order> findByCustomerId(String customerId);
	
	List<Order> findAllByDateBetween(Date startDate, Date endDate);

}
