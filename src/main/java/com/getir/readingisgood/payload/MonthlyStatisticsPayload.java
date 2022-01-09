package com.getir.readingisgood.payload;

public class MonthlyStatisticsPayload {

	private String month;
	private Integer orderCount;
	private Integer bookCount;
	private Integer purchasedAmount;
	
	public MonthlyStatisticsPayload() {
		super();
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getBookCount() {
		return bookCount;
	}

	public void setBookCount(Integer bookCount) {
		this.bookCount = bookCount;
	}

	public Integer getPurchasedAmount() {
		return purchasedAmount;
	}

	public void setPurchasedAmount(Integer purchasedAmount) {
		this.purchasedAmount = purchasedAmount;
	}
	
}
