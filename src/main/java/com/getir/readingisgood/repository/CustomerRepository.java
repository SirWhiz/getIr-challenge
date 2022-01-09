package com.getir.readingisgood.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.getir.readingisgood.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	List<Customer> findByEmail(String email);
	
	Optional<Customer> findByName(String username);

	Boolean existsByName(String username);
	
	Boolean existsByEmail(String email);
	
}
