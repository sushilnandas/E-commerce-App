package com.retail.e_com.entity;

import com.retail.e_com.enums.Priority;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="contact")
public class Contact {
	
	@Id
	private int contactId;
    private String name;
    private Priority priority;
    private long phoneNumber;
	private String email;
}
