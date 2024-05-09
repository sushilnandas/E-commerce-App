package com.retail.e_com.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


import com.retail.e_com.exception.InvalidUserEmailException;
import com.retail.e_com.exception.InvalidUserRoleException;
import com.retail.e_com.exception.OTPExpiredException;
import com.retail.e_com.exception.RegistrationSessionExpiredException;
import com.retail.e_com.exception.UserAlreadyExistEmailException;


public class ApplicationExceptionHandler {
	
	private ErrorStructure errorStructure;

	private ErrorStructure errorStructure(HttpStatus status,String message,String rootcause){
		return errorStructure.setStatus(status.value()).
				setMessage(message).setRootCause(rootcause);
	}


      
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> invalidUserRoleSpecified(InvalidUserRoleException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"Invalid User Role ",ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> otpExpired(OTPExpiredException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"OTP Expired",ex.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure> registartionSessionExpired(RegistrationSessionExpiredException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"Registration Sesion Expired",ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> userAllreadyExistByEmail(UserAlreadyExistEmailException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"User Already Exist By Email",ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure>  invalidUserEmailSpecified( InvalidUserEmailException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"Invalid email",ex.getMessage()));
	}

	
}
