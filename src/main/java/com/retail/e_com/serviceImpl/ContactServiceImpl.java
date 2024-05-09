package com.retail.e_com.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.retail.e_com.authService.ContactService;
import com.retail.e_com.entity.Contact;
import com.retail.e_com.exception.AddressNotFoundByIdException;
import com.retail.e_com.exception.ContactAlreadyExistException;
import com.retail.e_com.exception.ContactNotFoundByIdException;
import com.retail.e_com.repository.AddressRepository;
import com.retail.e_com.repository.ContactRepository;
import com.retail.e_com.requestdto.ContactRequest;
import com.retail.e_com.responsedto.ContactResponse;
import com.retail.e_com.utility.ResponseStructure;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {


	private AddressRepository addressRepository;
	private ContactRepository contactRepository;

	@Override
	public ResponseEntity<ResponseStructure<List<ContactResponse>>> addContact(List<ContactRequest> conatactRequests, int addressId) {

		List<Contact> list = addressRepository.findById(addressId).map(address->{

			if(address.getContact().size()>2)
				throw new ContactAlreadyExistException("Can't add more than 2 conatct");

			List<Contact> contacts=mapToContactList(conatactRequests);
			List<Contact> saveContacts = contactRepository.saveAll(contacts);
			address.getContact().addAll(saveContacts);			

			addressRepository.save(address);		
			return saveContacts;

		}).orElseThrow(()-> new AddressNotFoundByIdException("Address not found by Id"));
		return ResponseEntity.ok(new ResponseStructure<List<ContactResponse>>()
				.setData(mapToContactResponses(list)).setMessgae("Conatct added")
				.setStatus(HttpStatus.OK.value()));


	}

	@Override
	public ResponseEntity<ResponseStructure<ContactResponse>> updateContact(
			ContactRequest conatactRequests, int contactId) {
		Contact contact2 = contactRepository.findById(contactId).map(contact->{
			return contactRepository.save(mapToConatct(contact, conatactRequests));
		}).orElseThrow(()->new ContactNotFoundByIdException("contact not found by Id"));
		return ResponseEntity.ok(new ResponseStructure<ContactResponse>().setData(mapToConatctResponse(contact2)).setMessgae("update contact").setStatus(HttpStatus.OK.value()));
	}


	private List<Contact> mapToContactList(List<ContactRequest> conatactRequests) {
		List<Contact> contacts=new ArrayList<>();

		for(ContactRequest request: conatactRequests)
		{
			contacts.add(mapToConatct(new Contact() ,request));
		}
		return contacts;
	}


	private Contact mapToConatct(Contact contact, ContactRequest request) {
		contact.setEmail(request.getEmail());
		contact.setName(request.getName());
		contact.setPhoneNumber(request.getContactNumber());
		contact.setPriority(request.getPriority());
		return contact;
	}

	private List<ContactResponse> mapToContactResponses(List<Contact> list) {

		List<ContactResponse> contactResponses=new ArrayList<>();

		for(Contact contact:list)
		{
			contactResponses.add(mapToConatctResponse(contact));
		}
		return contactResponses;		
	}

	private ContactResponse mapToConatctResponse(Contact contact) {		
		return ContactResponse.builder()
				.contactId(contact.getContactId())
				.name(contact.getName())
				.phoneNumber(contact.getPhoneNumber())
				.email(contact.getEmail())
				.priority(contact.getPriority()).build();

	}




}
