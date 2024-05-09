package com.retail.e_com.entity;

import java.util.List;

import com.retail.e_com.enums.AddressType;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int addressId;
	private String streetAdddress;
	private String streetAddressAdditional;
	private String city;
	private String state;
	private String country;
	private long pincode;
	private AddressType addressType;
	
	@OneToMany(fetch=FetchType.EAGER)
	private List<Contact>contact;
}
