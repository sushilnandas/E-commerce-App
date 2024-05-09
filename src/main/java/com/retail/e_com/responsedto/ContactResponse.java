package com.retail.e_com.responsedto;

import com.retail.e_com.enums.Priority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Getter
@Builder
public class ContactResponse {
	
	
	private int contactId;
	private String name;
	private long phoneNumber;
	private String email;
	private Priority priority;

}
