package com.retail.e_com.jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.retail.e_com.repository.AccessRepository;
import com.retail.e_com.repository.RefreshRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
	private AccessRepository accessTokenRepository;
	private RefreshRepository refreshTokenRepository;
	private JWTService jwtService;
	
	

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		 String accessToken=null;
		 String refreshToken=null;
		if(cookies!=null)
		for(Cookie cookie:cookies)
		{
			if(cookie.getName().equals("at"))accessToken =cookie.getValue();
			
			if(cookie.getName().equals("rt"))refreshToken=cookie.getValue();
		}
		
		if(accessToken!=null && refreshToken!=null )
		{
			
			if(accessTokenRepository.existsByTokenAndIsBlocked(accessToken,true)&& refreshTokenRepository.existsByTokenAndIsBlocked(refreshToken,true))
				throw new RuntimeException();
			String username = jwtService.getUserName(accessToken);
			String userRole=jwtService.getUserRole(accessToken);
			if(username!=null && userRole!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
		
				 UsernamePasswordAuthenticationToken passwordAuthenticationToken = new  UsernamePasswordAuthenticationToken(username, null, Collections.singleton(new SimpleGrantedAuthority(userRole)));
				
				 passwordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}
	


}
