package com.retail.e_com.entity;

import com.retail.e_com.enums.AvailabiltyStatus;
import com.retail.e_com.enums.Category;
import com.retail.e_com.enums.SortDirectionBy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
public class Product {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String productName;
	private double productPrice;
	private int quantity;
	private AvailabiltyStatus availabiltyStatus;
	private Category category;
	private String productDescription;
	private int page;
	private SortDirectionBy sortDirectionBy;

}
