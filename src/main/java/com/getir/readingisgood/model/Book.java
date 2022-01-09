package com.getir.readingisgood.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {

	@Id
	private String id;
	
	private String name;
	private String pages;
	private Integer stock;
	
	public Book() {
		super();
	}

	public Book(String name, String pages, Integer stock) {
		super();
		this.name = name;
		this.pages = pages;
		this.stock = stock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getId() {
		return id;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", pages=" + pages + ", stock=" + stock + "]";
	}
	
}
