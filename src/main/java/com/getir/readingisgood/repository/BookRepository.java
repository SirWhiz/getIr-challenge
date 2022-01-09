package com.getir.readingisgood.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.getir.readingisgood.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {

}
