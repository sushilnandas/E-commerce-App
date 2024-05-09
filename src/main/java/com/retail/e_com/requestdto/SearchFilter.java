package com.retail.e_com.requestdto;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchFilter {

	int minprice;
	int maxPrice;
	String availability;
	int rating;
	int discount;
	String category;

}
