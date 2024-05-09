package com.retail.e_com.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.retail.e_com.authService.ContactService;
import com.retail.e_com.requestdto.ContactRequest;
import com.retail.e_com.responsedto.ContactResponse;
import com.retail.e_com.utility.ResponseStructure;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@CrossOrigin(origins="http://localhost:5173/")

public class ContactController {
private ContactService contactSerice;
	
	@PostMapping("/contact/{addressId}")
	public ResponseEntity<ResponseStructure<List<ContactResponse>>> addConact(@RequestBody List<ContactRequest> conatactRequests, @PathVariable int addressId)
	{
		return contactSerice.addContact(conatactRequests,addressId);
	}
	
	@PutMapping("/contact/{contactId}")
	public ResponseEntity<ResponseStructure<ContactResponse>> updateContact(@RequestBody ContactRequest conatactRequests, @PathVariable int contactId)
	{
		return contactSerice.updateContact(conatactRequests,contactId);
	}

}
