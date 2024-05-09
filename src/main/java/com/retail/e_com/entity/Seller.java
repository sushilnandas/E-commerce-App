package com.retail.e_com.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="seller")
public class Seller extends User {
	
	@OneToOne
	private Address address;
	@OneToMany
	private List<Product> products;
	

}
