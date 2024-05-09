package com.retail.e_com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.e_com.authService.AuthService;
import com.retail.e_com.entity.User;
import com.retail.e_com.jwt.JWTService;
import com.retail.e_com.requestdto.AuthRequest;
import com.retail.e_com.requestdto.OTPRequest;
import com.retail.e_com.requestdto.SearchFilter;
import com.retail.e_com.requestdto.UserRequest;
import com.retail.e_com.responsedto.AuthResponseDto;
import com.retail.e_com.responsedto.UserResponse;
import com.retail.e_com.utility.ResponseStructure;
import com.retail.e_com.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@CrossOrigin(origins="http://localhost:5173/")
@RequestMapping("/api/v1")
public class AuthController {
	private AuthService authService;
	private JWTService jwtService;

	@PostMapping("/register")
	public ResponseEntity<SimpleResponseStructure>registerUsers (@RequestBody UserRequest userRequest){
		return authService.registerUsers(userRequest);

	}

	//	@PostMapping("/test")
	//	private String test() {
	//		return "asdfgghssssss";
	//	}

	@PostMapping("/verifyemail")
	private ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(@RequestBody OTPRequest otp) {
		return authService.verifyOTP(otp);
	}

	//	@GetMapping("/test")
	//	public String test() {
	//		return jwtService.generateAccessToken("sssss");
	//	}
	//	
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponseDto>> login (@RequestBody AuthRequest authRequest){
		System.out.println(authRequest.getUsername());
		return authService.userLogin(authRequest);

	}
	@PostMapping("/logout")
	public ResponseEntity<SimpleResponseStructure> userLogout(@CookieValue(name = "at", required = false)String accessToken, @CookieValue(name = "rt",required = false)String refreshToken )
	{
		System.out.println(accessToken+" |"+refreshToken);
		return authService.userLogout(accessToken,refreshToken);
	}



}
