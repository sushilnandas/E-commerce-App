package com.retail.e_com.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;


@Service
public class JWTService {

	@Value("${myapp.jwt.secret}")
	private String secret;

	@Value("${myapp.jwt.access.expiration}")
	private long accessExpiry;

	@Value("${myapp.jwt.refresh.expiration}")
	private long refreshExpiry;

	public String generateAccessToken(String userName,String role) {
		return generateToken(userName,role,accessExpiry);
	}

	public String generateRefreshToken(String userName,String role) {
		return generateToken(userName,role,refreshExpiry);
	}

	private String generateToken(String userName, String role,long expiration) {
		return Jwts.builder()
				.setClaims(Maps.of("role", role).build())
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				
				.signWith(getSignatureKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	public Date getIssueDate(String token)
	{
		return parseJwtToken(token).getIssuedAt();
	}

	private Key getSignatureKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}
	
	public String getUserName(String token) {
		return parseJwtToken(token).getSubject();
	}
	public String getUserRole(String token) {
		return parseJwtToken(token).get("role", String.class);
	}
	private Claims parseJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();
	}



}






