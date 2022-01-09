package com.getir.readingisgood.payload;

public class UpdateBookStockPayload {

	private String bookId;
	private Integer stock;
	
	public UpdateBookStockPayload(String bookId, Integer stock) {
		this.bookId = bookId;
		this.stock = stock;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
}
