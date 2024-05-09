package com.retail.e_com.requestdto;

import com.retail.e_com.enums.AvailabiltyStatus;
import com.retail.e_com.enums.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
	
	private String productName;
	private double productPrice;
	private int quantity;
	private AvailabiltyStatus availabiltyStatus;
	private Category category;
	private String productDescription;

	

}
