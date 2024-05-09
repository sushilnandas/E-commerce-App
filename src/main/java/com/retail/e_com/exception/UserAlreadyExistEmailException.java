package com.retail.e_com.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SuppressWarnings("serial")
public class UserAlreadyExistEmailException extends RuntimeException{
	
	private String message;

	public String getMessage() {
		return message;
	}


	
	
	

}
