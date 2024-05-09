package com.retail.e_com.responsedto;

import java.util.List;

import com.retail.e_com.enums.AddressType;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class AddressResponse {

	private String streetAddress;
	private String streetAddressAdditional;
	private String city;
	private String state;
	private int addressId;
	private String country;
	private long pincode;
	@Enumerated(EnumType.STRING)@NotNull
	private  AddressType addressType;
	private List<ContactResponse> contacts;




}
