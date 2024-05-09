package com.retail.e_com.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@SuppressWarnings("serail")
public class InvalidUserRoleException extends RuntimeException {
	
	private String message;

}
