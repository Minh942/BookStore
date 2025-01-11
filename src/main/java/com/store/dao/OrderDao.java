package com.store.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.store.entity.Order;
import com.store.model.BestSellerModel;
import com.store.model.StatisticalOrder;
import com.store.model.StatisticalProductDay;

public interface OrderDao extends JpaRepository<Order, Integer>{
	@Query("SELECT o FROM Order o WHERE o.code = ?1")
	List<Order> getOrderByName(String code);


	@Query("SELECT new StatisticalProductDay(o.product.code, o.product.name, o.product.price, o.product.quality, sum(o.quality)) FROM Order o GROUP BY o.product.code, o.product.name, o.product.price, o.product.quality")
	List<StatisticalProductDay> listStatisticalProductDay();

	@Query("SELECT COUNT(o) FROM Order o  GROUP BY o.code")
	Integer getMaxOrderSuccessOnDay(int day, int month, int year);

	@Query("SELECT COUNT(o) FROM Order o GROUP BY o.code")
	Integer getMaxOrderTransportOnDay(int day, int month, int year);

	@Query("SELECT COUNT(o) FROM Order o GROUP BY o.code")
	Integer getMaxOrderWaitOnDay(int day, int month, int year);

	@Query("SELECT COUNT(o) FROM Order o GROUP BY o.code")
	Integer getMaxOrderCancelOnDay(int day, int month, int year);

	@Query("SELECT o.product, sum(o.quality) FROM Order o  GROUP BY o.product ORDER BY sum(o.quality) DESC")
	List<BestSellerModel> getListBestSellerProduct(Pageable pageable);
}
