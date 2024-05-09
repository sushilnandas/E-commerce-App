package com.retail.e_com.requestdto;

import com.retail.e_com.enums.AddressType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AddressRequest {

	private String streetAddress;
	private String streetAddressAdditional;
	private String city;
	private String state;
	private String country;
	private long pincode;
	@Enumerated(EnumType.STRING)@NotNull
	private  AddressType addressType;

}
