package com.retail.e_com.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter

public class Customer extends User {
      
	@OneToMany
	private List<Address> address ; 
	
	
}
