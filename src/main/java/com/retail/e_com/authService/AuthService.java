package com.retail.e_com.authService;

import org.springframework.http.ResponseEntity;

import com.retail.e_com.requestdto.AddressRequest;
import com.retail.e_com.requestdto.AuthRequest;
import com.retail.e_com.requestdto.OTPRequest;
import com.retail.e_com.requestdto.UserRequest;
import com.retail.e_com.responsedto.AddressResponse;
import com.retail.e_com.responsedto.AuthResponseDto;
import com.retail.e_com.responsedto.UserResponse;
import com.retail.e_com.utility.ResponseStructure;
import com.retail.e_com.utility.SimpleResponseStructure;


public interface AuthService {

	ResponseEntity<SimpleResponseStructure> registerUsers(UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OTPRequest otpRequest);
	
	ResponseEntity<ResponseStructure<AuthResponseDto>> userLogin(AuthRequest authRequest);
	
	ResponseEntity<SimpleResponseStructure> userLogout(String refreshToken, String accessToken);
    ResponseEntity<ResponseStructure<AuthResponseDto>> refreshLogin(String accessToken, String refreshToken);
	
	

}
