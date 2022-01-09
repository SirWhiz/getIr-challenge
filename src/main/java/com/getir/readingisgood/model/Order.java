package com.getir.readingisgood.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {

	@Id
	private String id;
	
	private Date date;
	private String customerId;
	private List<Book> books;
	private Integer amount;
	
	public Order() {
		super();
	}
	
	public Order(Date date, String customerId, List<Book> books, Integer amount) {
		this.date = date;
		this.customerId = customerId;
		this.books = books;
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getMonthFromDate() {
		SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.ENGLISH);

		String month_name = month_date.format(date);
		return month_name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", customerId=" + customerId + ", books=" + books + ", amount="
				+ amount + "]";
	}
	
}
