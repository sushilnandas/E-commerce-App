package com.retail.e_com.authService;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.retail.e_com.requestdto.ContactRequest;
import com.retail.e_com.responsedto.ContactResponse;

import com.retail.e_com.utility.ResponseStructure;



public interface ContactService {
	ResponseEntity<ResponseStructure<List<ContactResponse>>> addContact(List<ContactRequest> conatactRequests, int addressId);

	ResponseEntity<ResponseStructure<ContactResponse>> updateContact(ContactRequest conatactRequests,int contactId);


}
