package com.store.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class Product implements Serializable{
	// Thong tin id san pham
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// Thong tin ma san pham
	private String code;

	// Thong tin ten san pham
	private String name;

	// Thong tin gia san pham
	private int price;

	// Thong tin so luong san pham
	private int quality;


	// Mo ta san pham
	private String description;


	// Thong tin hinh anh 1
	private String image1;

	// Thong tin hinh anh 2
	private String image2;

	// Thong tin hinh anh 3
	private String image3;

	// Thong tin gia khuyen mai
	private int sales;

	// Thong tin ngay cap nhat
	private String Updateday;

	// Thong tin danh muc
	@ManyToOne
	@JoinColumn(name = "Cate_Id")
	Category category;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
	List<Order> listOrder;

}
