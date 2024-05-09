package com.retail.e_com.exception;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@SuppressWarnings("serial")

public class ContactNotFoundByIdException extends RuntimeException {
	
	private String message;

}
