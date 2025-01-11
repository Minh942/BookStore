package com.store.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.store.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer>{
	@Query("SELECT p FROM Product p")
	List<Product> getListProduct();
	
	@Query(value="SELECT  p FROM Product p")
	List<Product> getListLatestProduct();
	
	@Query(value="SELECT  p FROM Product p")
	List<Product> getListViewsProduct();
	
	@Query("SELECT p FROM Product p WHERE p.category.Namesearch LIKE ?1  ")
	Page<Product> getListProductByNameSearch(String nameSearch, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.category.Namesearch LIKE ?1  AND p.price >= ?2 AND p.price <= ?3 ")
	Page<Product> getListProductByPrice(String nameSearch, int minPrice, int maxPrice, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.category.Namesearch LIKE ?1 ")
	List<Product> getListDemo(String nameSearch);
	
	@Query("SELECT p FROM Product p")
	Product getProductByNameSearch(String nameSearch);
	
	@Query(value="SELECT p FROM Product p")
	List<Product> getListProductRelated(int manuId);
	
	@Query(value="SELECT p FROM Product p")
	List<Product> getListProductSales();
	
	@Query(value="SELECT * FROM ProductS WHERE NOT EXISTS (SELECT * FROM ORDERS WHERE Products.Id = ORDERS.Product_Id) AND ProductS.DeleteDay is NULL", nativeQuery = true)
	List<Product> listStatisticalProductWarehouse();
}
