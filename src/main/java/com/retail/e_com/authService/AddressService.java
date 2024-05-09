package com.retail.e_com.authService;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.retail.e_com.requestdto.AddressRequest;
import com.retail.e_com.responsedto.AddressResponse;
import com.retail.e_com.utility.ResponseStructure;



public interface AddressService {
	

	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, String accessToken );

	public ResponseEntity<ResponseStructure<List<AddressResponse>>> findAddressByUser(String accessToken);

	public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(AddressRequest addressRequest,int addressId); 

}
