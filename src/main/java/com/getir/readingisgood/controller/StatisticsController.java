package com.getir.readingisgood.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getir.readingisgood.model.Order;
import com.getir.readingisgood.payload.MonthlyStatisticsPayload;
import com.getir.readingisgood.payload.ResponseBean;
import com.getir.readingisgood.repository.OrderRepository;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
	
	@Autowired
	OrderRepository orderRepository;

	@GetMapping("/{customerId}")
	public ResponseBean getMonthlyStatistics(@PathVariable("customerId") String orderId) {
		ResponseBean response = new ResponseBean();
		
		try {
			List<Order> customerTotalOrders = orderRepository.findByCustomerId(orderId);
			
			Map<String, List<Order>> orderListGrouped =
					customerTotalOrders.stream().collect(Collectors.groupingBy(o -> o.getMonthFromDate()));
			
			List<MonthlyStatisticsPayload> statisticsList = new ArrayList<MonthlyStatisticsPayload>();
			for (var entry : orderListGrouped.entrySet()) {
				MonthlyStatisticsPayload monthStatistics = new MonthlyStatisticsPayload();
				monthStatistics.setMonth(entry.getKey());
				
				int orderCount = 0;
				int bookCount = 0;
				int purchasedAmount = 0;
				
				for (Order order : entry.getValue()) {
					orderCount += 1;
					bookCount += order.getBooks().size();
					purchasedAmount += order.getAmount();
				}
				
				monthStatistics.setOrderCount(orderCount);
				monthStatistics.setBookCount(bookCount);
				monthStatistics.setPurchasedAmount(purchasedAmount);
				statisticsList.add(monthStatistics);
			}
			
			response.setData(statisticsList);
			response.setStatus(HttpStatus.OK.toString());
		}catch (Exception e) {
			response.setData(null);
	    	response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		}
		
		return response;
	}
	
}
