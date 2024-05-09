package com.retail.e_com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.e_com.authService.AddressService;
import com.retail.e_com.responsedto.ProductResponse;
import com.retail.e_com.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins="http://localhost:5173/")
@RequestMapping("/api/v1")
public class CartProductController {
	
	
	public ResponseEntity<ResponseStructure<ProductResponse>> addProductToCart(){
		return null;
	}

}
