package com.store.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.store.entity.Order;
import com.store.entity.Product;
import com.store.model.BestSellerModel;
import com.store.model.DetailOrder;
import com.store.model.OrderModel;
import com.store.model.StatisticalProductDay;
import com.store.model.StatisticalRevenue;
import com.store.model.StatisticalTotalOrder;

public interface OrderService {

	List<Order> getOrderByName(String code);

	void save(Order order);

	List<OrderModel> listOrderHistory();

	List<Order> listOrderByCodeAndUsername(String id);

	List<OrderModel> listOrderGroupByCode();

	DetailOrder getDetailOrderByCode(String id);

	void approveOrder(String id);

	void cancelOrder(String id);

	List<OrderModel> listOrderGroupByCodeShipping();

	void shippedOrder(String id);

	List<OrderModel> listOrderGroupByCodeSuccess();

	List<OrderModel> listOrderGroupByCodeCancel();

	void deleteOrder(String id);

	List<StatisticalProductDay> listStatisticalProductDay();
	
	List<StatisticalRevenue> listStatisticalRevenue(int month, int year);

	List<StatisticalRevenue> listStatisticalRevenueByMonth(int year);

	List<StatisticalRevenue> listStatisticalRevenueByYear(int year);

	StatisticalTotalOrder getStatisticalTotalOrderOnDay(int day, int month, int year);

	StatisticalTotalOrder getStatisticalTotalOrderOnMonth(int month, int year);

	StatisticalTotalOrder getStatisticalTotalOrderOnYear(int year);

	List<Integer> getListYearOrder();

	StatisticalTotalOrder getStatisticalTotalOrderOnOption(int day, int month, int year);

	List<BestSellerModel> getListBestSellerProduct(Pageable topFour);

	List<Product> listStatisticalProductWarehouse();

}
