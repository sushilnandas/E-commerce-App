package com.retail.e_com.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@SuppressWarnings("serial")
public class AlreadyHaveAddressException extends RuntimeException {
	private String messsage;

}
