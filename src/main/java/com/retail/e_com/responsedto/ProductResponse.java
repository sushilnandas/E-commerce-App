package com.retail.e_com.responsedto;

import com.retail.e_com.enums.AvailabiltyStatus;
import com.retail.e_com.enums.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
	private int productId;
	private String productName;
	private double productPrice;
	private int quantity;
	private String productDescription;
	private AvailabiltyStatus availabiltyStatus;
	private Category category;

}
