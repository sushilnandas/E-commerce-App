package com.retail.e_com.responsedto;

import com.retail.e_com.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AuthResponseDto {
	private String username;
	private int userId;
	private UserRole userRole;
	private long accessToken;
	private long refreshToken;
	

}
