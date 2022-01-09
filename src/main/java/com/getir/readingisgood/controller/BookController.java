package com.getir.readingisgood.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.payload.ResponseBean;
import com.getir.readingisgood.payload.UpdateBookStockPayload;
import com.getir.readingisgood.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	BookRepository bookRepository;
	
	@GetMapping
	public ResponseBean getAllBooks() {
	    ResponseBean response = new ResponseBean();
	    
	    try {
	    	response.setData(bookRepository.findAll());
	    	response.setStatus(HttpStatus.OK.toString());
	    }catch (Exception e) {
	    	response.setData(null);
	    	response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
	    }
	    
	    return response;
	}
	
	@PostMapping
	public ResponseBean saveBook(@RequestBody Book newBook) {
		ResponseBean response = new ResponseBean();
		
		try {
			bookRepository.save(newBook);
			response.setStatus(HttpStatus.CREATED.toString());
		}catch (Exception e) {
			response.setData(null);
			response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		}
		
		return response;
	}
	
	@PostMapping("/stock/update")
	public ResponseBean updateBookStock(@RequestBody UpdateBookStockPayload bookStockData) {
		ResponseBean response = new ResponseBean();
		
		try {
			Optional<Book> book = bookRepository.findById(bookStockData.getBookId());
			if(book.isPresent()) {
				Book bookToUpdate = book.get();
				bookToUpdate.setStock(bookStockData.getStock());
				bookRepository.save(bookToUpdate);
			}
			response.setStatus(HttpStatus.OK.toString());
		}catch (Exception e) {
			response.setData(null);
			response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		}
		
		return response;
	}
	
}
