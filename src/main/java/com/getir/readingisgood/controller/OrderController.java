package com.getir.readingisgood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.Order;
import com.getir.readingisgood.payload.OrdersBetweenPayload;
import com.getir.readingisgood.payload.ResponseBean;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.OrderRepository;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@GetMapping
	public ResponseBean getAllOrders() {
	    ResponseBean response = new ResponseBean();
	    
	    try {
	    	response.setData(orderRepository.findAll());
	    	response.setStatus(HttpStatus.OK.toString());
	    }catch (Exception e) {
	    	response.setData(null);
	    	response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
	    }
	    
	    return response;
	}
	
	@GetMapping("/{orderId}")
	public ResponseBean getOrder(@PathVariable("orderId") String orderId) {
		ResponseBean response = new ResponseBean();
		
	    try {
	    	response.setData(orderRepository.findById(orderId));
	    	response.setStatus(HttpStatus.OK.toString());
	    }catch (Exception e) {
	    	response.setData(null);
	    	response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
	    }
		
		return response;
	}
	
	@PostMapping
	public ResponseBean createNewOrder(@RequestBody Order newOrder) {
		ResponseBean response = new ResponseBean();
		
		try {
			if(newOrder.getAmount() <= 0) {
				response.setData("You can't get paid to buy books!");
				response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				return response;
			}
			orderRepository.save(newOrder);
			updateStock(newOrder);
			response.setStatus(HttpStatus.CREATED.toString());
		}catch (Exception e) {
			response.setData(null);
			response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		}
		
		return response;
	}
	
	@PostMapping("/getOrdersBetween")
	public ResponseBean getOrdersInsideDateRange(@RequestBody OrdersBetweenPayload data) {
		ResponseBean response = new ResponseBean();
		
		try {
			response.setData(orderRepository.findAllByDateBetween(data.getFrom(), data.getTo()));
			response.setStatus(HttpStatus.OK.toString());
		}catch (Exception e) {
			response.setData(null);
			response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		}
		
		return response;
	}
	
	private void updateStock(Order order) {
		for (Book book : order.getBooks()) {
			book.setStock(book.getStock() - 1);
			bookRepository.save(book);
		}
	}

}
