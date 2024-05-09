package com.retail.e_com.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductNotFoundByIdException extends RuntimeException {

	private String message;
}
