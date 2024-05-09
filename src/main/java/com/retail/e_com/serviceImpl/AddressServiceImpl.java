package com.retail.e_com.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.retail.e_com.authService.AddressService;
import com.retail.e_com.entity.Address;
import com.retail.e_com.entity.Contact;
import com.retail.e_com.entity.Customer;
import com.retail.e_com.entity.Seller;
import com.retail.e_com.enums.UserRole;
import com.retail.e_com.exception.AddressNotFoundByIdException;
import com.retail.e_com.exception.AddressNotFoundByUserException;
import com.retail.e_com.exception.AlreadyHaveAddressException;
import com.retail.e_com.jwt.JWTService;
import com.retail.e_com.repository.AddressRepository;
import com.retail.e_com.repository.CustomerRepository;
import com.retail.e_com.repository.SellerRepository;
import com.retail.e_com.repository.UserRepository;
import com.retail.e_com.requestdto.AddressRequest;
import com.retail.e_com.responsedto.AddressResponse;
import com.retail.e_com.responsedto.ContactResponse;
import com.retail.e_com.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private SellerRepository sellerRepository;
	private CustomerRepository customerRepository;
	private JWTService jwtService;

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, String accessToken) {
		
		String username=jwtService.getUserName(accessToken);
		
		Optional<Address> optional = userRepository.findByUsername(username).map(user->{
			System.out.println(username);
			Address address=null;
			
			if(user.getUserRole().equals(UserRole.SELLER))
			{
				if(((Seller)user).getAddress()!=null)
					throw new AlreadyHaveAddressException("seller alredy have address can't add  ");
				 address = addressRepository.save(mapToAddress(new Address(),addressRequest));
				((Seller)user).setAddress(address);	
				sellerRepository.save(((Seller)user));
			}
			else if(user.getUserRole().equals(UserRole.CUSTOMER)) 
			{
				if(((Customer)user).getAddress().size()>5)
					throw new AlreadyHaveAddressException("customer can't have more than 5 address");
				 address = addressRepository.save(mapToAddress(new Address(),addressRequest));
				((Customer)user).getAddress().add(address);	
				customerRepository.save(((Customer)user));
			}
			return address;
		});
		return ResponseEntity.ok(new ResponseStructure<AddressResponse>()
				.setData(mapToAddressResponse(optional.get())).setMessgae("address added")
				.setStatus(HttpStatus.OK.value()));
		
	}

	@Override
	public ResponseEntity<ResponseStructure<List<AddressResponse>>> findAddressByUser(String accessToken) {
		
		 
		String username=jwtService.getUserName(accessToken);
		AtomicReference<List<Address>> addresses = new AtomicReference<>(null);
		
		AtomicReference<List<Address>> atomicReference = userRepository.findByUsername(username).map(user->{
			
			if(user.getUserRole().equals(UserRole.SELLER))
			{
				Address address = ((Seller)user).getAddress();
				if(address==null)
					throw new AddressNotFoundByUserException("the user don't have address");
				addresses.set(List.of(address));			
			}
			else if(user.getUserRole().equals(UserRole.CUSTOMER))
			{
				addresses.set(((Customer)user).getAddress());
				if(addresses.get()==null)
					throw new AddressNotFoundByUserException("the user don't have address");	
			}
			return addresses;
		}).orElseThrow(()->new UsernameNotFoundException("user not found"));
		
	return ResponseEntity.ok(new ResponseStructure<List<AddressResponse>>()
			.setData(mapToAddressResponseList(atomicReference))
			.setMessgae("Address Found")
			.setStatus(HttpStatus.OK.value()));	
	}	
	
	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(AddressRequest addressRequest,
			int addressId) {
		Address address2 = addressRepository.findById(addressId).map(address ->{
			return addressRepository.save(mapToAddress(address, addressRequest));
			
		}).orElseThrow(()->new AddressNotFoundByIdException("address not found by id"));
		return ResponseEntity.ok(new ResponseStructure<AddressResponse>()
				.setData(mapToAddressResponse(address2))
				.setMessgae("address updated")
				.setStatus(HttpStatus.OK.value()));
	}
	
	private List<AddressResponse> mapToAddressResponseList(AtomicReference<List<Address>> atomicReference)
	{
		List<Address> list = atomicReference.get();
		List<AddressResponse> addressResponses=new ArrayList<>();
		for(Address address: list)
		{
			addressResponses.add(mapToAddressResponse(address));
		}
		return addressResponses;
	}	

	private AddressResponse mapToAddressResponse(Address address) {
		List<ContactResponse> mapToContactResponses =null;
		if(address.getContact()!=null) {
			mapToContactResponses= mapToContactResponses(address.getContact());
		}
		return AddressResponse
		.builder().addressId(address.getAddressId())
				  .streetAddress(address.getStreetAdddress())
				  .streetAddressAdditional(address.getStreetAddressAdditional())
				  .city(address.getCity())
				  .pincode(address.getPincode())
				  .state(address.getState())
				  .addressType(address.getAddressType())
				  .contacts( mapToContactResponses ).build();
						
		
	}

	private Address mapToAddress(Address address, AddressRequest addressRequest) {		
		address.setAddressType(addressRequest.getAddressType());
		address.setCity(addressRequest.getCity());
		address.setPincode(addressRequest.getPincode());
		address.setState(addressRequest.getState());
		address.setStreetAdddress(addressRequest.getStreetAddress());
		address.setStreetAddressAdditional(addressRequest.getStreetAddressAdditional());
		return address;
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
