package com.getir.readingisgood.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getir.readingisgood.model.Order;
import com.getir.readingisgood.payload.ResponseBean;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	OrderRepository orderRepository;

	@GetMapping
	public ResponseBean getAllCustomers() {
	    ResponseBean response = new ResponseBean();
	    
	    try {
	    	response.setData(customerRepository.findAll());
	    	response.setStatus(HttpStatus.OK.toString());
	    }catch (Exception e) {
	    	response.setData(null);
	    	response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
	    }
	    
	    return response;
	}
	
	@GetMapping("/orders/{customerId}")
	public ResponseBean getCustomerOrders(@PathVariable("customerId") String customerId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
		ResponseBean response = new ResponseBean();
		
		try {
			Pageable paging = PageRequest.of(page, size);
			Page<Order> pageOrder = orderRepository.findByCustomerId(customerId, paging);
			List<Order> orders = pageOrder.getContent();
			
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("orders", orders);
			responseMap.put("currentPage", pageOrder.getNumber());
			responseMap.put("totalItems", pageOrder.getTotalElements());
			responseMap.put("totalPages", pageOrder.getTotalPages());
			
			response.setData(responseMap);
	    	response.setStatus(HttpStatus.OK.toString());
	    }catch (Exception e) {
	    	response.setData(null);
	    	response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
	    }
		
		return response;
	}
	
}
