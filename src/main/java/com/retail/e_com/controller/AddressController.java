package com.retail.e_com.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.e_com.authService.AddressService;
import com.retail.e_com.requestdto.AddressRequest;
import com.retail.e_com.responsedto.AddressResponse;
import com.retail.e_com.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin(origins="http://localhost:5173/")
@RequestMapping("/api/v1")
@RestController
public class AddressController {

	private AddressService addressService;
	
	@PostMapping("/address")
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@RequestBody  AddressRequest addressRequest, @CookieValue(name = "at",required = false) String accessToken)
	{
		return addressService.addAddress(addressRequest, accessToken);		
	}
	
	@GetMapping("/address")
	public ResponseEntity<ResponseStructure<List<AddressResponse>>> findAddressByUser(@CookieValue(name = "at",required = false)String accessToken)
	{
		return addressService.findAddressByUser(accessToken);
	}
	
	@PutMapping("/address/{addressId}")
	public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(@RequestBody AddressRequest addressRequest, @PathVariable int addressId)
	{
		return addressService.updateAddress(addressRequest,addressId);
	}


}
